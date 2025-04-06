package com.musinsa.ohj.infrastructure.database.querydsl;

import com.musinsa.ohj.domain.model.dto.querydsl.param.ProductInfoByCondOrderByBrdSeqCtgSeqParamDto;
import com.musinsa.ohj.domain.model.dto.querydsl.query.ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto;
import com.musinsa.ohj.domain.model.dto.querydsl.query.QProductInfoByCondOrderByBrdSeqCtgSeqQueryDto;
import com.musinsa.ohj.domain.model.dto.querydsl.query.QRank1SumOfBrdLowestPriceQueryDto;
import com.musinsa.ohj.domain.model.dto.querydsl.query.QRankProductPriceByCtgSeqQueryDto;
import com.musinsa.ohj.domain.model.dto.querydsl.query.QSummaryLowestPriceByCtgAndBrdQueryDto;
import com.musinsa.ohj.domain.model.dto.querydsl.query.Rank1SumOfBrdLowestPriceQueryDto;
import com.musinsa.ohj.domain.model.dto.querydsl.query.RankProductPriceByCtgSeqQueryDto;
import com.musinsa.ohj.domain.model.dto.querydsl.query.SummaryLowestPriceByCtgAndBrdQueryDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.musinsa.ohj.domain.model.entity.QBrand.brand;
import static com.musinsa.ohj.domain.model.entity.QCategory.category;
import static com.musinsa.ohj.domain.model.entity.QProduct.product;

@Repository
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProductFetchQueryDslImpl extends CommonQueryDslCondImpl {

    JPAQueryFactory jpaQueryFactory;

    public List<SummaryLowestPriceByCtgAndBrdQueryDto> findSummaryLowestPriceByCtgAndBrd() {
        JPAQuery<Tuple> lowestPriceByCategorySubQuery = jpaQueryFactory.select(product.category.categorySeq,
                product.productPrice.min())
                .from(product)
                .groupBy(product.category.categorySeq);

        return jpaQueryFactory.select(new QSummaryLowestPriceByCtgAndBrdQueryDto(category.categorySeq,
                category.categoryNm,
                brand.brandNm,
                product.productPrice))
                .from(product)
                .innerJoin(product.brand, brand)
                .innerJoin(product.category, category)
                .where(Expressions.booleanTemplate(
                        "( {0}, {1} ) IN ({2})",
                        category.categorySeq,
                        product.productPrice,
                        lowestPriceByCategorySubQuery
                ))
                .orderBy(category.categorySeq.asc())
                .fetch();
    }

    public List<Rank1SumOfBrdLowestPriceQueryDto> findRank1SumOfBrdLowestPrice() {
        NumberPath<Integer> rankAlias = Expressions.numberPath(Integer.class, "rank");
        List<Rank1SumOfBrdLowestPriceQueryDto> rank1SumOfBrdLowestPrices
                = jpaQueryFactory.select(new QRank1SumOfBrdLowestPriceQueryDto(brand.brandSeq,
                product.productPrice.sum(),
                Expressions.numberTemplate(Integer.class,
                        "RANK() OVER (ORDER BY {0})",
                        product.productPrice.sum()).as(rankAlias)))
                .from(product)
                .innerJoin(product.brand, brand)
                .groupBy(product.brand.brandSeq)
                .orderBy(rankAlias.asc())
                .fetch();
        return rank1SumOfBrdLowestPrices.stream()
                .takeWhile(v -> v.rank() == 1)
                .collect(Collectors.toList());
    }

    public List<ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto> findProductInfoByCondOrderByBrdSeqCtgSeq(
            ProductInfoByCondOrderByBrdSeqCtgSeqParamDto productInfoByCondOrderByBrdSeqCtgSeqParamDto) {
        return jpaQueryFactory.select(new QProductInfoByCondOrderByBrdSeqCtgSeqQueryDto(
                product.productSeq,
                brand.brandSeq,
                category.categorySeq,
                brand.brandNm,
                category.categoryNm,
                product.productNm,
                product.productPrice))
                .from(product)
                .innerJoin(product.brand, brand)
                .innerJoin(product.category, category)
                .where(inBrandSeq(productInfoByCondOrderByBrdSeqCtgSeqParamDto.brandSeqs()))
                .orderBy(brand.brandSeq.asc(), category.categorySeq.asc())
                .fetch();
    }

    public List<RankProductPriceByCtgSeqQueryDto> findRankProductPriceByCtgSeq(Long categorySeq) {
        NumberPath<Integer> rankAlias = Expressions.numberPath(Integer.class, "rank");
        return jpaQueryFactory.select(new QRankProductPriceByCtgSeqQueryDto(product.productSeq,
                brand.brandNm,
                product.productPrice,
                Expressions.numberTemplate(Integer.class,
                        "RANK() OVER (ORDER BY {0})",
                        product.productPrice).as(rankAlias)))
                .from(product)
                .innerJoin(product.brand, brand)
                .innerJoin(product.category, category)
                .where(category.categorySeq.eq(categorySeq))
                .orderBy(rankAlias.asc())
                .fetch();
    }
}
