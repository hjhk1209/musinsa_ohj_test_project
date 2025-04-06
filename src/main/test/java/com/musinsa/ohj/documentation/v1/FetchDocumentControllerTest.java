package com.musinsa.ohj.documentation.v1;

import com.musinsa.ohj.application.FetchService;
import com.musinsa.ohj.documentation.CommonDocumentTest;
import com.musinsa.ohj.domain.model.constants.ApiResponseCode;
import com.musinsa.ohj.domain.model.dto.querydsl.query.ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto;
import com.musinsa.ohj.domain.model.dto.querydsl.query.Rank1SumOfBrdLowestPriceQueryDto;
import com.musinsa.ohj.domain.model.dto.service.fetch.HighestPriceAndLowestPriceByCategoryNmDto;
import com.musinsa.ohj.domain.model.dto.service.fetch.HighestPriceAndLowestPriceByCategoryNmItemDto;
import com.musinsa.ohj.domain.model.dto.service.fetch.SummaryLowestPriceByCtgAndBrdDto;
import com.musinsa.ohj.domain.model.dto.service.fetch.SummaryLowestPriceByCtgAndBrdItemDto;
import com.musinsa.ohj.presentation.response.fetch.HighestPriceAndLowestPriceByCategoryNmResponse;
import com.musinsa.ohj.presentation.response.fetch.SumLowestPriceByBrdResponse;
import com.musinsa.ohj.presentation.response.fetch.SummaryLowestPriceByCtgAndBrdResponse;
import com.musinsa.ohj.presentation.v1.FetchController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FetchController.class)
public class FetchDocumentControllerTest extends CommonDocumentTest {

    @MockBean
    FetchService fetchService;

    @Test
    void fetchSummaryLowestPriceByCtgAndBrdDocument() throws Exception {
        List<SummaryLowestPriceByCtgAndBrdItemDto> summaryLowestPriceByCtgAndBrdItemDtos =
                List.of(SummaryLowestPriceByCtgAndBrdItemDto.builder()
                                .categoryNm("상의")
                                .brandNmList(Set.of("C"))
                                .productPrice(String.format("%,d", 10000))
                                .build(),
                        SummaryLowestPriceByCtgAndBrdItemDto.builder()
                                .categoryNm("스니커즈")
                                .brandNmList(Set.of("A", "G"))
                                .productPrice(String.format("%,d", 9000))
                                .build());

        given(fetchService.fetchSummaryLowestPriceByCtgAndBrd())
                .willReturn(SummaryLowestPriceByCtgAndBrdResponse.builder(ApiResponseCode.SUCCESS)
                        .data(SummaryLowestPriceByCtgAndBrdDto.builder()
                                .list(summaryLowestPriceByCtgAndBrdItemDtos)
                                .totalProductPrice(String.format("%,d", 19000))
                                .build())
                        .build());

        mockMvc.perform(get("/v1/summary/lowest-price/by-category-brand"))
                .andExpect(status().isOk())
                .andDo(document("fetch/summaryLowestPriceByCtgAndBrd",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(commonResponseFieldWithPaths(
                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                        .optional()
                                        .description("데이터 필드 (값이 존재하지 않을 경우 표시되지 않음)"))
                        ).andWithPrefix("data.",
                                fieldWithPath("list").type(JsonFieldType.ARRAY)
                                        .optional().description("목록"),
                                fieldWithPath("list.[].categoryNm").type(JsonFieldType.STRING)
                                        .optional().description("카테고리명"),
                                fieldWithPath("list.[].brandNmList").type(JsonFieldType.ARRAY)
                                        .optional().description("브랜드명 목록"),
                                fieldWithPath("list.[].productPrice").type(JsonFieldType.STRING)
                                        .optional().description("상품 가격"),
                                fieldWithPath("totalProductPrice").type(JsonFieldType.STRING)
                                        .optional().description("상품 총액")
                        )
                ));
    }

    @Test
    void fetchSumLowestPriceByBrd() throws Exception {
        List<Rank1SumOfBrdLowestPriceQueryDto> rank1SumOfBrdLowestPriceQueryDtos =
                List.of(Rank1SumOfBrdLowestPriceQueryDto.builder()
                                .brandSeq(4l)
                                .sumProductPrice(15000l)
                                .rank(1)
                                .build(),
                        Rank1SumOfBrdLowestPriceQueryDto.builder()
                                .brandSeq(7l)
                                .sumProductPrice(15000l)
                                .rank(1)
                                .build());

        Map<Long, List<ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto>> productInfoByCondMap =
                List.of(ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto.builder()
                                .brandSeq(4l)
                                .brandNm("D")
                                .categoryNm("상의")
                                .productPrice(10000l)
                                .build(),
                        ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto.builder()
                                .brandSeq(4l)
                                .brandNm("D")
                                .categoryNm("스니커즈")
                                .productPrice(5000l)
                                .build(),
                        ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto.builder()
                                .brandSeq(7l)
                                .brandNm("G")
                                .categoryNm("상의")
                                .productPrice(11000l)
                                .build(),
                        ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto.builder()
                                .brandSeq(7l)
                                .brandNm("G")
                                .categoryNm("스니커즈")
                                .productPrice(4000l)
                                .build())
                        .stream()
                        .collect(Collectors.groupingBy(ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto::brandSeq));

        given(fetchService.fetchSumLowestPriceByBrd())
                .willReturn(SumLowestPriceByBrdResponse.builder(ApiResponseCode.SUCCESS)
                        .data(rank1SumOfBrdLowestPriceQueryDtos.stream()
                                .map(v -> v.toSumLowestPriceByBrdDto(productInfoByCondMap))
                                .collect(Collectors.toList()))
                        .build());

        mockMvc.perform(get("/v1/summary/sum-lowest-price/by-brand"))
                .andExpect(status().isOk())
                .andDo(document("fetch/sumLowestPriceByBrd",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(commonResponseFieldWithPaths(
                                fieldWithPath("data").type(JsonFieldType.ARRAY)
                                        .optional()
                                        .description("데이터 필드 (값이 존재하지 않을 경우 표시되지 않음)"))
                        ).andWithPrefix("data.[].",
                                fieldWithPath("lowest").type(JsonFieldType.OBJECT)
                                        .optional().description("최저가"),
                                fieldWithPath("lowest.brandNm").type(JsonFieldType.STRING)
                                        .optional().description("브랜드명"),
                                fieldWithPath("lowest.categoryList").type(JsonFieldType.ARRAY)
                                        .optional().description("카테고리 목록"),
                                fieldWithPath("lowest.categoryList.[].categoryNm").type(JsonFieldType.STRING)
                                        .optional().description("브랜드명 목록"),
                                fieldWithPath("lowest.categoryList.[].productPrice").type(JsonFieldType.STRING)
                                        .optional().description("상품 가격"),
                                fieldWithPath("lowest.totalProductPrice").type(JsonFieldType.STRING)
                                        .optional().description("상품 총액")
                        )
                ));
    }

    @Test
    void fetchHighestPriceAndLowestPriceByCategoryNm() throws Exception {
        String categoryNm = "상의";
        given(fetchService.fetchHighestPriceAndLowestPriceByCategoryNm(any(String.class)))
                .willReturn(HighestPriceAndLowestPriceByCategoryNmResponse.builder(ApiResponseCode.SUCCESS)
                        .data(HighestPriceAndLowestPriceByCategoryNmDto.builder()
                                .categoryNm(categoryNm)
                                .lowestList(List.of(HighestPriceAndLowestPriceByCategoryNmItemDto.builder()
                                                .brandNm("C")
                                                .productPrice(String.format("%,d", 1500))
                                                .build(),
                                        HighestPriceAndLowestPriceByCategoryNmItemDto.builder()
                                                .brandNm("D")
                                                .productPrice(String.format("%,d", 1500))
                                                .build()))
                                .highestList(List.of(HighestPriceAndLowestPriceByCategoryNmItemDto.builder()
                                                .brandNm("A")
                                                .productPrice(String.format("%,d", 5000))
                                                .build(),
                                        HighestPriceAndLowestPriceByCategoryNmItemDto.builder()
                                                .brandNm("G")
                                                .productPrice(String.format("%,d", 5000))
                                                .build()))
                                .build())
                        .build());

        mockMvc.perform(get("/v1/category/highest-price-lowest-price")
                .param("categoryNm", categoryNm))
                .andExpect(status().isOk())
                .andDo(document("fetch/highestPriceAndLowestPriceByCategoryNm",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("categoryNm")
                                        .description("카테고리명")
                        ),
                        responseFields(commonResponseFieldWithPaths(
                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                        .optional()
                                        .description("데이터 필드 (값이 존재하지 않을 경우 표시되지 않음)"))
                        ).andWithPrefix("data.",
                                fieldWithPath("categoryNm").type(JsonFieldType.STRING)
                                        .optional().description("카테고리명"),
                                fieldWithPath("lowestList").type(JsonFieldType.ARRAY)
                                        .optional().description("최저가 리스트"),
                                fieldWithPath("lowestList.[].brandNm").type(JsonFieldType.STRING)
                                        .optional().description("최저가 브랜드명"),
                                fieldWithPath("lowestList.[].productPrice").type(JsonFieldType.STRING)
                                        .optional().description("최저가 상품가격"),
                                fieldWithPath("highestList").type(JsonFieldType.ARRAY)
                                        .optional().description("최고가 리스트"),
                                fieldWithPath("highestList.[].brandNm").type(JsonFieldType.STRING)
                                        .optional().description("최고가 브랜드명"),
                                fieldWithPath("highestList.[].productPrice").type(JsonFieldType.STRING)
                                        .optional().description("최고가 상품가격")
                        )
                ));
    }
}
