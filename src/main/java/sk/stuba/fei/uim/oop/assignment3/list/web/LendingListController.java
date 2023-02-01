package sk.stuba.fei.uim.oop.assignment3.list.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.list.logic.ILendingListService;
import sk.stuba.fei.uim.oop.assignment3.list.web.bodies.BookInListRequest;
import sk.stuba.fei.uim.oop.assignment3.list.web.bodies.LendingListResponse;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/list")
public class LendingListController {
    @Autowired
    private ILendingListService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LendingListResponse> getAllLendingLists() {
        return this.service.getAll().stream().map(LendingListResponse::new).collect(Collectors.toList());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LendingListResponse> createLendingList() {
        return new ResponseEntity<>(new LendingListResponse(this.service.create()), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LendingListResponse getLendingListById(@PathVariable("id") Long lendingListId) throws NotFoundException {
        return new LendingListResponse(this.service.getById(lendingListId));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteLendingList(@PathVariable("id") Long lendingListId) throws NotFoundException {
        this.service.delete(lendingListId);
    }

    @PostMapping(value = "/{id}/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LendingListResponse> addBookToLendingList(@PathVariable("id") Long lendingListId, @RequestBody BookInListRequest request) throws NotFoundException, IllegalOperationException {
        return new ResponseEntity<>(new LendingListResponse(this.service.addBookToLendingList(lendingListId, request)), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/remove", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void removeBookFromLendingList(@PathVariable("id") Long lendingListId, @RequestBody BookInListRequest request) throws NotFoundException {
        this.service.removeBookFromList(lendingListId, request);
    }

    @GetMapping(value = "/{id}/lend")
    public void lendTheLendingList(@PathVariable("id") Long lendingListId) throws NotFoundException, IllegalOperationException {
        this.service.lendLendingList(lendingListId);
    }

}

