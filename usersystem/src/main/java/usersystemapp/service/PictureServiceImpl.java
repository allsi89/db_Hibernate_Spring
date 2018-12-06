package usersystemapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usersystemapp.repository.PictureRepository;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;

    @Autowired
    public PictureServiceImpl(final PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }
}
