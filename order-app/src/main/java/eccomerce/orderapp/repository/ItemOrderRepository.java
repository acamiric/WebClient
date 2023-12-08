package eccomerce.orderapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eccomerce.itemorder.ItemOrder;


@Repository
public interface ItemOrderRepository extends JpaRepository<ItemOrder, Long> {

}
