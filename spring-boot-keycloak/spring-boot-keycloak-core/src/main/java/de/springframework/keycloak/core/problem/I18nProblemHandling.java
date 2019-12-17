package de.springframework.keycloak.core.problem;

import org.zalando.problem.spring.web.advice.ProblemHandling;

public interface I18nProblemHandling extends
        ProblemHandling,
        ConcurrencyFailureAdviceTrait,
        BadRequestAdviceTrait,
        NoSuchElementAdviceTrait {

}
