package com.musinsa.ohj.application;

import com.musinsa.ohj.domain.model.constants.ApiResponseCode;
import com.musinsa.ohj.domain.model.dto.querydsl.param.ProductInfoByCondOrderByBrdSeqCtgSeqParamDto;
import com.musinsa.ohj.domain.model.dto.querydsl.query.ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto;
import com.musinsa.ohj.domain.model.dto.querydsl.query.Rank1SumOfBrdLowestPriceQueryDto;
import com.musinsa.ohj.domain.model.dto.querydsl.query.RankProductPriceByCtgSeqQueryDto;
import com.musinsa.ohj.domain.model.dto.querydsl.query.SummaryLowestPriceByCtgAndBrdQueryDto;
import com.musinsa.ohj.domain.model.dto.service.fetch.HighestPriceAndLowestPriceByCategoryNmDto;
import com.musinsa.ohj.domain.model.dto.service.fetch.HighestPriceAndLowestPriceByCategoryNmItemDto;
import com.musinsa.ohj.domain.model.dto.service.fetch.SummaryLowestPriceByCtgAndBrdDto;
import com.musinsa.ohj.domain.model.dto.service.fetch.SummaryLowestPriceByCtgAndBrdItemDto;
import com.musinsa.ohj.domain.model.entity.Category;
import com.musinsa.ohj.infrastructure.database.querydsl.ProductFetchQueryDslImpl;
import com.musinsa.ohj.infrastructure.database.repository.CategoryJpaRepository;
import com.musinsa.ohj.presentation.response.fetch.HighestPriceAndLowestPriceByCategoryNmResponse;
import com.musinsa.ohj.presentation.response.fetch.SumLowestPriceByBrdResponse;
import com.musinsa.ohj.presentation.response.fetch.SummaryLowestPriceByCtgAndBrdResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class FetchService {

    ProductFetchQueryDslImpl productFetchQueryDslImpl;

    CategoryJpaRepository categoryJpaRepository;

    public SummaryLowestPriceByCtgAndBrdResponse fetchSummaryLowestPriceByCtgAndBrd() {
        List<SummaryLowestPriceByCtgAndBrdQueryDto> summaryLowestPriceByCtgAndBrdQueryDtos
                = productFetchQueryDslImpl.findSummaryLowestPriceByCtgAndBrd();
        if (CollectionUtils.isEmpty(summaryLowestPriceByCtgAndBrdQueryDtos)) {
            return SummaryLowestPriceByCtgAndBrdResponse.builder(ApiResponseCode.SUCCESS)
                    .build();
        }
        /** 구현3)에서 요청값이 카테고리명인거보면 카테고리명도 유일한 값이라 판단
         * 카테고리에서 최저가인 브랜드가 다수일 수 있으니 브랜드명 응답시 배열로 처리하기 위함
         * **/
        List<SummaryLowestPriceByCtgAndBrdItemDto> summaryLowestPriceByCtgAndBrdItemDtos =
                summaryLowestPriceByCtgAndBrdQueryDtos.stream()
                        .collect(Collectors.groupingBy(
                                value -> new AbstractMap.SimpleEntry<>(value.categoryNm(), value.productPrice()),
                                LinkedHashMap::new,
                                /** 동일한 브랜드는 나오지 않도록 하기 위해 set으로 처리 **/
                                Collectors.mapping(SummaryLowestPriceByCtgAndBrdQueryDto::brandNm, Collectors.toSet()))
                        )
                        .entrySet()
                        .stream()
                        .map(entry -> SummaryLowestPriceByCtgAndBrdItemDto.builder()
                                .categoryNm(entry.getKey().getKey())
                                .brandNmList(entry.getValue())
                                .productPrice(String.format("%,d", entry.getKey().getValue()))
                                .build())
                        .collect(Collectors.toList());
        Long totalProductPrice = summaryLowestPriceByCtgAndBrdItemDtos.stream()
                .mapToLong(value -> Long.parseLong(value.productPrice().replaceAll("[^\\d]", "")))
                .sum();
        return SummaryLowestPriceByCtgAndBrdResponse.builder(ApiResponseCode.SUCCESS)
                .data(SummaryLowestPriceByCtgAndBrdDto.builder()
                        .list(summaryLowestPriceByCtgAndBrdItemDtos)
                        .totalProductPrice(String.format("%,d", totalProductPrice))
                        .build())
                .build();
    }

    public SumLowestPriceByBrdResponse fetchSumLowestPriceByBrd() {
        /** 단일 브랜드로 모든 카테고리 상품 구매시 최저가격 다수일 수 있으니 응답시 배열로 처리하기 위함 **/
        List<Rank1SumOfBrdLowestPriceQueryDto> rank1SumOfBrdLowestPriceQueryDtos
                = productFetchQueryDslImpl.findRank1SumOfBrdLowestPrice();
        if (CollectionUtils.isEmpty(rank1SumOfBrdLowestPriceQueryDtos)) {
            return SumLowestPriceByBrdResponse.builder(ApiResponseCode.SUCCESS)
                    .build();
        }
        Map<Long, List<ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto>> productInfoByCondMap =
                productFetchQueryDslImpl.findProductInfoByCondOrderByBrdSeqCtgSeq(ProductInfoByCondOrderByBrdSeqCtgSeqParamDto.builder()
                        .brandSeqs(rank1SumOfBrdLowestPriceQueryDtos.stream()
                                .map(Rank1SumOfBrdLowestPriceQueryDto::brandSeq)
                                .collect(Collectors.toList()))
                        .build())
                        .stream()
                        .collect(Collectors.groupingBy(ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto::brandSeq));
        return SumLowestPriceByBrdResponse.builder(ApiResponseCode.SUCCESS)
                .data(rank1SumOfBrdLowestPriceQueryDtos.stream()
                        .map(v -> v.toSumLowestPriceByBrdDto(productInfoByCondMap))
                        .collect(Collectors.toList()))
                .build();
    }

    public HighestPriceAndLowestPriceByCategoryNmResponse fetchHighestPriceAndLowestPriceByCategoryNm(String categoryNm) {
        Optional<Category> categoryOptional = categoryJpaRepository.findByCategoryNm(categoryNm);
        if (!categoryOptional.isPresent()) {
            return HighestPriceAndLowestPriceByCategoryNmResponse.builder(ApiResponseCode.SUCCESS)
                    .build();
        }
        Category category = categoryOptional.get();
        List<RankProductPriceByCtgSeqQueryDto> rankProductPriceByCtgSeqQueryDtos =
                productFetchQueryDslImpl.findRankProductPriceByCtgSeq(category.getCategorySeq());
        return HighestPriceAndLowestPriceByCategoryNmResponse.builder(ApiResponseCode.SUCCESS)
                .data(HighestPriceAndLowestPriceByCategoryNmDto.builder()
                        .categoryNm(category.getCategoryNm())
                        .lowestList(rankProductPriceByCtgSeqQueryDtos.stream()
                                .takeWhile(v -> v.rank() == 1)
                                .map(RankProductPriceByCtgSeqQueryDto::toHighestPriceAndLowestPriceByCategoryNmItemDto)
                                .collect(Collectors.toList()))
                        .highestList(getRankProductPriceByCtgSeqQueryDtoHighestList(rankProductPriceByCtgSeqQueryDtos))
                        .build())
                .build();
    }

    private List<HighestPriceAndLowestPriceByCategoryNmItemDto> getRankProductPriceByCtgSeqQueryDtoHighestList(
            List<RankProductPriceByCtgSeqQueryDto> rankProductPriceByCtgSeqQueryDtos) {
        List<HighestPriceAndLowestPriceByCategoryNmItemDto> highestList = new ArrayList();
        Integer maxRank = null;

        ListIterator<RankProductPriceByCtgSeqQueryDto> rankProductPriceByCtgSeqQueryDtoListIterator =
                rankProductPriceByCtgSeqQueryDtos.listIterator(rankProductPriceByCtgSeqQueryDtos.size());
        while (rankProductPriceByCtgSeqQueryDtoListIterator.hasPrevious()) {
            RankProductPriceByCtgSeqQueryDto preValue = rankProductPriceByCtgSeqQueryDtoListIterator.previous();
            if (maxRank == null) {
                maxRank = preValue.rank();
            } else if (maxRank != preValue.rank()) {
                break;
            }
            highestList.add(preValue.toHighestPriceAndLowestPriceByCategoryNmItemDto());
        }
        return highestList;
    }
}
