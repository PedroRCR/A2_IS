package com.example.A2_IS.controllers;

import com.example.A2_IS.models.Relation;
import com.example.A2_IS.service.RelationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/a2/relation")
@RequiredArgsConstructor
@Slf4j
public class RelationController {

    @Autowired
    RelationService relationService;

    @PostMapping("/create")
    public Mono<Relation> createRelation(@RequestBody Relation relation){return relationService.createRelation(relation);}

    @GetMapping
    public Flux<Relation> getAllRelations(){return relationService.getAllRelation();}


}
