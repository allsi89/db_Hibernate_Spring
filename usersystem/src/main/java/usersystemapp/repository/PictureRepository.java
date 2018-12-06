package usersystemapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import usersystemapp.models.Picture;

@Repository
public interface PictureRepository  extends JpaRepository<Picture, Long> {
}
