package ru.itis.springsemwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.springsemwork.models.Item;
import ru.itis.springsemwork.models.OrderItem;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Item, Long> {

    @Query("select i from Item i where LOWER(i.name) like LOWER(CONCAT('%', :query, '%')) or lower(i.description) like LOWER(CONCAT('%', :query, '%')) and i.state = 'ACTIVE'")
    List<Item> findByNameContainingIgnoreCase(@Param("query") String query);

    List<Item> getAllByItemTypeAndState(Item.ItemType itemType, Item.State state);

    @Query("select i from Item i where i.id in :ids")
    List<Item> findAllFromOrderItem(@Param("ids") List<Long> ids);

    @Query(value = "SELECT last_value FROM items_id_seq", nativeQuery = true)
    Long getLastItemIndex();

    List<Item> findAllByState(Item.State state);
}
