package it.vincendep.popcorn.integration.job.common;

import it.vincendep.popcorn.integration.omdb.exception.OmdbException;
import it.vincendep.popcorn.integration.tmdb.exception.TmdbException;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.stereotype.Component;

@Component
public class PopcornJobSkipPolicy implements SkipPolicy {

    private static final int MAX_SKIPS = 500;

    @Override
    public boolean shouldSkip(Throwable t, int skipCount) throws SkipLimitExceededException {
        if (skipCount > MAX_SKIPS) {
            throw new SkipLimitExceededException(skipCount, t);
        }
        return t instanceof TmdbException || t instanceof OmdbException;
    }
}
