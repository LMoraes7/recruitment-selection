package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.question;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question.CreateQuestionUseCase;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.context.SecurityEmployeeContext;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.question.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.question.request.QuestionRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
@Validated
public final class QuestionController {
    private final CreateQuestionUseCase createQuestionUseCase;

    @Autowired
    public QuestionController(final CreateQuestionUseCase createQuestionUseCase) {
        this.createQuestionUseCase = createQuestionUseCase;
    }

    @PostMapping
    public ResponseEntity<?> createQuestion(@RequestBody @Valid final QuestionRequest request) {
        this.createQuestionUseCase.execute(
                SecurityEmployeeContext.getContext(),
                ConverterHelper.toDto(request)
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
