package Restaurant.example.Restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Restaurant.example.Restaurant.model.TableEntity;

@Repository
public interface TableEntityRepository  extends JpaRepository<TableEntity, Long>{

}
