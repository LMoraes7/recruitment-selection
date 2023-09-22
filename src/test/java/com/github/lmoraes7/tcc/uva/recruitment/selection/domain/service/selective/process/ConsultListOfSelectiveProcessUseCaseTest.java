package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.ConsultSelectiveProcessPagineted;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.SelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto.PaginationQuery;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto.SelectiveProcessoPaginated;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.SelectiveProcessRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Set;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

final class ConsultListOfSelectiveProcessUseCaseTest {
    private final SelectiveProcessRepository selectiveProcessRepository = mock(SelectiveProcessRepository.class);
    private final ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
    private final ConsultListOfSelectiveProcessUseCase consultListOfSelectiveProcessUseCase = new ConsultListOfSelectiveProcessUseCase(this.selectiveProcessRepository, this.applicationEventPublisher);

    private PaginationQuery paginationQuery;
    private SelectiveProcessoPaginated selectiveProcessoPaginated;

    @BeforeEach
    void setUp() {
        this.paginationQuery = dummyObject(PaginationQuery.class);
        this.selectiveProcessoPaginated = new SelectiveProcessoPaginated(
                List.of(new SelectiveProcess(
                        GeneratorIdentifier.forSelectiveProcess(),
                        dummyObject(String.class),
                        dummyObject(String.class),
                        dummyObject(String.class),
                        dummyObject(StatusSelectiveProcess.class),
                        Set.of(dummyObject(String.class)),
                        Set.of(dummyObject(String.class)),
                        Set.of(dummyObject(String.class)),
                        List.of(dummyObject(ExternalStep.class), dummyObject(ExternalStep.class), dummyObject(ExternalStep.class), dummyObject(ExternalStep.class))
                )),
                dummyObject(Integer.class),
                dummyObject(Integer.class),
                dummyObject(Integer.class),
                dummyObject(Integer.class),
                dummyObject(Long.class)
        );
    }

    @Test
    void when_requested_you_must_perform_a_paged_query_successfully() {
        when(this.selectiveProcessRepository.findAll(this.paginationQuery)).thenReturn(this.selectiveProcessoPaginated);

        assertDoesNotThrow(() -> this.consultListOfSelectiveProcessUseCase.execute(this.paginationQuery));

        verify(this.selectiveProcessRepository, only()).findAll(this.paginationQuery);
        verify(this.applicationEventPublisher, only()).publishEvent(any(ConsultSelectiveProcessPagineted.class));
    }
}