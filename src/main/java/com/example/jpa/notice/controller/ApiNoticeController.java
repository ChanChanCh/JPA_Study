package com.example.jpa.notice.controller;

import com.example.jpa.notice.entity.Notice;
import com.example.jpa.notice.execption.AlreadyDeletedException;
import com.example.jpa.notice.execption.DuplicateNoticeException;
import com.example.jpa.notice.execption.NoticeNotFundException;
import com.example.jpa.notice.model.NoticeDeleteInput;
import com.example.jpa.notice.model.NoticeInput;
import com.example.jpa.notice.model.NoticeModel;
import com.example.jpa.notice.model.ResponseError;
import com.example.jpa.notice.repository.NoticeRepository;
import jdk.vm.ci.meta.Local;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
public class ApiNoticeController {

    private final NoticeRepository noticeRepository;


    /*
    @GetMapping("/api/notice")
    public String noticeString(){
        return "공지사항입니다.";
    }
    */

    /*
    @GetMapping("/api/notice")
    public NoticeModel notice() {

        LocalDateTime regDate = LocalDateTime.of(2021, 2, 8, 0, 0);

        NoticeModel notice = new NoticeModel();
        notice.setId(1);
        notice.setTitle("공지사항입니다.");
        notice.setContents("공지사항 내용입니다.");
        notice.setRegDate(regDate);

        return notice;
    }
    */

    /*
    @GetMapping("/api/notice")
    public List<NoticeModel> notice() {

        List<NoticeModel> noticeList = new ArrayList<>();

        noticeList.add(NoticeModel.builder()
                .id(1)
                .title("공지사항입니다.")
                .contents("공지사항내용입니다.")
                .regDate(LocalDateTime.of(2021, 1, 30, 0, 0))
                .build());
        noticeList.add(NoticeModel.builder()
                .id(2)
                .title("두번째 공지사항입니다.")
                .contents("두번째 공지사항내용입니다.")
                .regDate(LocalDateTime.of(2021, 1, 31, 0, 0))
                .build());

        return noticeList;
    }
    */

    @GetMapping("/api/notice")
    public List<NoticeModel> notice() {
        List<NoticeModel> noticeList = new ArrayList<>();

        return noticeList;
    }

    @GetMapping("/api/notice/count")
    public int noticeCount() {

        return 20;
    }


    /*
    @PostMapping("/api/notice")
    public NoticeModel addNotice(@RequestParam String title, @RequestParam String contents) {

        NoticeModel notice = NoticeModel.builder()
                .id(1)
                .title(title)
                .contents(contents)
                .regDate(LocalDateTime.now())
                .build();

        return notice;
    }
    */

    /*
    @PostMapping("/api/notice")
    public NoticeModel addNotice(NoticeModel noticeModel) {

        noticeModel.setId(2);
        noticeModel.setRegDate(LocalDateTime.now());

        return noticeModel;
    }*/

    /*
    @PostMapping("/api/notice")
    public NoticeModel addNotice(@RequestBody NoticeModel noticeModel) {

        noticeModel.setId(3);
        noticeModel.setRegDate(LocalDateTime.now());

        return noticeModel;
    }
    */

    /*
    @PostMapping("/api/notice")
    public Notice addNotice(@RequestBody NoticeInput noticeInput) {

        Notice notice = Notice.builder()
                .title(noticeInput.getTitle())
                .contnets(noticeInput.getContents())
                .regDate(LocalDateTime.now())
                .build();

        noticeRepository.save(notice);

        return notice;
    }
    */

    /*
    @PostMapping("/api/notice")
    public Notice addNotice(@RequestBody NoticeInput noticeInput) {

        Notice notice = Notice.builder()
                .title(noticeInput.getTitle())
                .contents(noticeInput.getContents())
                .regDate(LocalDateTime.now())
                .hits(0)
                .likes(0)
                .build();

        Notice resultNotice = noticeRepository.save(notice);

        return resultNotice;
    }
    */

    @GetMapping("/api/notice/{id}")
    public Notice notice(@PathVariable Long id) {

        Optional<Notice> notice = noticeRepository.findById(id);
        if (notice.isPresent()) {
            return notice.get();
        }

        return null;
    }

    /*
    @PutMapping("/api/notice/{id}")
    public void updateNotice(@PathVariable Long id
            , @RequestBody NoticeInput noticeInput) {

        Optional<Notice> notice = noticeRepository.findById(id);
        if (notice.isPresent()) {
            notice.get().setTitle(noticeInput.getTitle());
            notice.get().setContents(noticeInput.getContents());
            notice.get().setUpdateDate(LocalDateTime.now());
            noticeRepository.save(notice.get());
        }

    }
    */

    @ExceptionHandler(NoticeNotFundException.class)
    public ResponseEntity<String> handlerNoticeNotFundException(NoticeNotFundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /*
    @PutMapping("/api/notice/{id}")
    public void updateNotice(@PathVariable Long id, @RequestBody NoticeInput noticeInput) {

        //Optional<Notice> notice = noticeRepository.findById(id);
        //if (!notice.isPresent()) {
            //예외 발생
            throw new NoticeNotFundException("공지사항의 글이 존재하지 않습니다.");
        //}

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFundException("공지사항의 글이 존재하지 않습니다."));

        //공지사항 글이 있을때
        notice.setTitle(noticeInput.getTitle());
        notice.setContents(noticeInput.getContents());
        notice.setUpdateDate(LocalDateTime.now());
        noticeRepository.save(notice);
    }
    */


    @PutMapping("/api/notice/{id}")
    public void updateNotice(@PathVariable Long id, @RequestBody NoticeInput noticeInput) {

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFundException("공지사항의 글이 존재하지 않습니다."));

        notice.setTitle(noticeInput.getTitle());
        notice.setContents(noticeInput.getContents());
        notice.setUpdateDate(LocalDateTime.now());
        noticeRepository.save(notice);
    }


    @PatchMapping("/api/notice/{id}/hits")
    public void noticeHits(@PathVariable Long id) {

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFundException("공지사항의 글이 존재하지 않습니다."));

        notice.setHits(notice.getHits() + 1);

        noticeRepository.save(notice);
    }


    /**
     21. 공지사항의 글을 삭제하기 위한 API를 만들어 보세요.
     [조건]
     - REST API 형식으로 구현
     - HTTP METHOD 는 DELETE
     - 요청 주소는 "/api/notices/1" ("1"은 공지사항의 글ID로 동적으로 변함)
     */
    /*
    @DeleteMapping("/api/notice/{id}")
    public void deleteNotice(@PathVariable Long id) {

        Optional<Notice> notice = noticeRepository.findById(id);
        if (notice.isPresent()) {
            noticeRepository.delete(notice.get());
        }
    }
    */

    /**
     22. 공지사항의 글을 삭제하기 위한 API를 만들어 보세요.
     [조건]
     - REST API 형식으로 구현
     - HTTP METHOD 는 DELETE
     - 요청 주소는 "/api/notices2/1" ("1"은 공지사항의 글ID로 동적으로 변함)
     - 전달받은 공지사항의 내용이 조회가 되자 않는 경우 NotFundException을 발생시킴
     - 이에 대한 결과는 400에러와 "내용이 존재하지 않습니다." 라는 메시지를 리턴함
     */
    /*
    @DeleteMapping("/api/notice/{id}")
    public void deleteNotice(@PathVariable Long id) {

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFundException("공지사항의 글이 존재하지 않습니다."));

        noticeRepository.delete(notice);
    }
    */

    @ExceptionHandler(AlreadyDeletedException.class)
    public ResponseEntity<String> handlerAlreadyDeletedException(AlreadyDeletedException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.OK);
    }

    /**
     23. 공지사항의 글을 삭제하기 위한 API를 만들어 보세요.
     [조건]
     - REST API 형식으로 구현
     - HTTP METHOD 는 DELETE
     - 요청 주소는 "/api/notices3/1" ("1"은 공지사항의 글ID로 동적으로 변함)
     - 게시판의 글을 물리적으로 삭제하지 않고 삭제 플래그값을 이용하여 삭제를 진행함
     */
    @DeleteMapping("/api/notice/{id}")
    public void deleteNotice(@PathVariable Long id) {

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFundException("공지사항의 글이 존재하지 않습니다."));

        if (notice.isDeleted()) {
            throw new AlreadyDeletedException("이미 삭제된 글입니다.");
        }

        notice.setDeleted(true);
        notice.setDeletedDate(LocalDateTime.now());

        noticeRepository.save(notice);
    }

    /**
     24. 공지사항의 글을 삭제하기 위한 API를 만들어 보세요.
     [조건]
     - REST API 형식으로 구현
     - HTTP METHOD는 DELETE
     - 요청 주소는 "/api/notices4/1" ("1"은 공지사항의 글ID로 동적으로 변함)
     - 사용자아이디가 파라미터로 전달되며, 공지사항 작성자와 동일한 경우 삭제 진행
     - 동일하지 않을 경우 400에러와 "본인의 작성한 글만 삭제하실 수 있습니다." 라는 메시지 리턴
     */
    @DeleteMapping("/api/notice")
    public void deleteNoticeList(@RequestBody NoticeDeleteInput noticeDeleteInput) {

        List<Notice> noticeList = noticeRepository.findByIdIn(noticeDeleteInput.getIdList())
                .orElseThrow(() -> new NoticeNotFundException("공지사항의 글이 존재하지 않습니다."));

        noticeList.stream().forEach(e -> {
            e.setDeleted(true);
            e.setDeletedDate(LocalDateTime.now());
        });

        noticeRepository.saveAll(noticeList);
    }

    /**
     25. 공지사항의 모든 글을 삭제하기 위한 API를 만들어 보세요.
     [조건]
     - REST API 형식으로 구현
     - HTTP METHOD는 DELETE
     - 요청 주소는 "/api/notices5"
     */
    @DeleteMapping("/api/notice/all")
    public void deleteAll() {

        noticeRepository.deleteAll();

    }


    /**
     26. 글을 작성할때 제목과 내용을 받아서 저장하는 API를 만들어 보세요.
     [조건]
     METHOD : POST
     DTO를 통한 파라미터를 형태로 받음
     등록일은 현재일시, 조회수, 좋아요수는 0으로 설정
     전달받은 파라미터를 통해서 데이터베이스에 저장함.
     */
    /*
    @PostMapping("/api/notice")
    public void addNotice(@RequestBody NoticeInput noticeInput) {

        Notice notice = Notice.builder()
                .title(noticeInput.getTitle())
                .contents(noticeInput.getContents())
                .hits(0)
                .likes(0)
                .regDate(LocalDateTime.now())
                .build();

        noticeRepository.save(notice);
    }
    */


    /**
     27. 글을 작성할때 제목과 내용을 받아서 저장하는 API를 만드어 보세요.
     [조건]
     입력값은 입력DTO를 통해서 입력받음
     제목과 내용은 필수 입력 조건임(입력되지 않은 경우 400 리턴)
     예외발생시 각각의 에러를 취합하여 콜렉션형태로 리턴"
     */

    /*
    @PostMapping("/api/notice")
    public ResponseEntity<Object> addNotice(@RequestBody @Valid NoticeInput noticeInput
            , Errors errors) {

        if (errors.hasErrors()) {
            List<ResponseError> responseErrors = new ArrayList<>();

            errors.getAllErrors().stream().forEach(e-> {
                responseErrors.add(ResponseError.of((FieldError)e));
            });

            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }

        noticeRepository.save(Notice.builder()
                .title(noticeInput.getTitle())
                .contents(noticeInput.getContents())
                .hits(0)
                .likes(0)
                .regDate(LocalDateTime.now())
                .build());

        return ResponseEntity.ok().build();

    }
    */

    /**
     28. 글을 작성할때 제목과 내용을 받아서 저장하는 API를 만드어 보세요.
     [조건]
     입력값은 입력DTO를 통해서 입력받음
     제목과 내용은 필수 입력 조건임(입력되지 않은 경우 400 리턴)
     제목의 경우 10자이상 100자 이하로 입력
     내용의 경우 50자이상 1000자 이하로 입력
     예외발생시 각각의 에러를 취합하여 콜렉션형태로 리턴
     */
    /*
    @PostMapping("/api/notice")
    public ResponseEntity<Object> addNotice(@RequestBody @Valid NoticeInput noticeInput
            , Errors errors) {

        if (errors.hasErrors()) {
            List<ResponseError> responseErrors = new ArrayList<>();

            errors.getAllErrors().stream().forEach(e -> {
                responseErrors.add(ResponseError.of((FieldError) e));
            });

            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }

        noticeRepository.save(Notice.builder()
                .title(noticeInput.getTitle())
                .contents(noticeInput.getContents())
                .hits(0)
                .likes(0)
                .regDate(LocalDateTime.now())
                .build());

        return ResponseEntity.ok().build();
    }
    */

    /**
     29. 데이터베이스에서 공지사항 목록중에서 파라미터로 전달된 개수만큼 리턴하는 API를 만들어 보세요.
     ex)최근5개
     */
    @GetMapping("/api/notice/latest/{size}")
    public Page<Notice> noticeLatest(@PathVariable int size) {

        Page<Notice> noticeList
                = noticeRepository.findAll(
                PageRequest.of(0, size, Sort.Direction.DESC, "regDate"));

        return noticeList;
    }

    @ExceptionHandler(DuplicateNoticeException.class)
    public ResponseEntity<?> handlerDuplicateNoticeException(DuplicateNoticeException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     30. 공지사항의 내용을 등록한 이후에 바로 동일한 제목과 내용의 공지사항을 등록하는 경우 등록을 막는 API를 만들어 보세요.
     중복 경우(조건: 동일제목, 동일내용과 등록일이 현재시간 기준 1분이내의 경우는 중복으로 판단함)
     예외발생(DuplicateNoticeException)
     */
    @PostMapping("/api/notice")
    public void addNotice(@RequestBody NoticeInput noticeInput) {

        //중복체크
        LocalDateTime checkDate = LocalDateTime.now().minusMinutes(1);
        int noticeCount = noticeRepository.countByTitleAndContentsAndRegDateIsGreaterThanEqual(
                noticeInput.getTitle()
                , noticeInput.getContents()
                , checkDate);
        if (noticeCount > 0) {
            throw new DuplicateNoticeException("1분이내에 등록된 동일한 공지사항이 존재합니다.");
        }

        noticeRepository.save(Notice.builder()
                .title(noticeInput.getTitle())
                .contents(noticeInput.getContents())
                .hits(0)
                .likes(0)
                .regDate(LocalDateTime.now())
                .build());
    }


}



















