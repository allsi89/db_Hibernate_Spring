package usersystemapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import usersystemapp.repository.AlbumRepository;

public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumServiceImpl(final AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }
}
