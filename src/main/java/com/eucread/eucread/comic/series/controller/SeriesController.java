package com.eucread.eucread.comic.series.controller;

import com.eucread.eucread.comic.series.dto.SeriesCreateRequestDto;
import com.eucread.eucread.comic.series.dto.SeriesDetailViewResponseDto;
import com.eucread.eucread.comic.series.dto.SeriesResponseDto;
import com.eucread.eucread.comic.series.dto.SeriesStatusUpdateRequestDto;
import com.eucread.eucread.comic.series.entity.Series;
import com.eucread.eucread.comic.series.service.SeriesService;
import com.eucread.eucread.util.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SeriesController {
    private final SeriesService seriesService;

    // 1. 시리즈 등록 (완료)
    @PostMapping("/CREATOR/comic/series/create")
    public ResponseEntity<Response<Void>> createSeries(@RequestBody SeriesCreateRequestDto seriesCreateRequestDto) {
        seriesService.createSeries(seriesCreateRequestDto);
        return Response.ok().toResponseEntity();
    }
    // 2. 시리즈 삭제 (완료)
    @DeleteMapping("/CREATOR/comic/series/{id}/delete")
    public ResponseEntity<Response<Void>> deleteSeries(@PathVariable Long id) {
        seriesService.deleteSeries(id);
        return Response.ok().toResponseEntity();
    }

    // 3. 시리즈 수정 (완료)
    @PatchMapping("/CREATOR/comic/series/{id}/patch")
    public ResponseEntity<Response<Void>> updateSeries(@RequestBody SeriesCreateRequestDto seriesCreateRequestDto, @PathVariable Long id) { // dto 재탕
        seriesService.patchSeries(seriesCreateRequestDto, id);
        return Response.ok().toResponseEntity();
    }

    // 4. 모든 시리즈 조회 (완료)
    @GetMapping("/comic/series")
    public ResponseEntity<Response<List<SeriesResponseDto>>> getPublishedSeries(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        // 쿼리파라미터로 페이지 받고 한 페이지당 10개 할 예정 다만 웹툰처럼 요일별로 할거면 수정 필요 정렬 기준이 좀 애매한데 일단 조회수 순으로 하겠슨
        List<SeriesResponseDto> seriesResponseDtoList = seriesService.getPublishedSeries(page, size);
        return Response.ok(seriesResponseDtoList).toResponseEntity();
    }

    // 5. 시리즈 상세보기 (완료)
    @GetMapping("/comic/series/{id}") // 개인정보가 들어간게 아니기에 그냥 이렇게 넣었슨
    public ResponseEntity<Response<SeriesDetailViewResponseDto>> getSeriesById(@PathVariable Long id) {
        SeriesDetailViewResponseDto seriesDetailViewResponseDto = seriesService.getSeriesById(id);
        return Response.ok(seriesDetailViewResponseDto).toResponseEntity();
    }


    // 6. 시리즈 관리자 작품 상태 조정 (완료)
    @PostMapping("/ADMIN/series/update")
    public ResponseEntity<Response<Void>> updateSeriesStatus(@RequestBody SeriesStatusUpdateRequestDto seriesStatusUpdateRequestDto, @PathVariable Long id) {
        seriesService.updateSeriesStatus(seriesStatusUpdateRequestDto);
        return Response.ok().toResponseEntity();
    }
}
