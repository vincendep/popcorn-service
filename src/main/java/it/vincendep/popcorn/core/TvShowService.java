package it.vincendep.popcorn.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TvShowService {

    private final TvShowRepository repository;
}
