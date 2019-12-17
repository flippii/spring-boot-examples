package de.springframework.keycloak.core.configuration;

import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(EnableCoreModule.EnableCoreModuleImportSelector.class)
public @interface EnableCoreModule {

    class EnableCoreModuleImportSelector implements ImportSelector {

        @NonNull
        @Override
        public String[] selectImports(@Nullable AnnotationMetadata importingClassMetadata) {
            return new String[] { CoreModuleConfiguration.class.getName() };
        }

    }

}
