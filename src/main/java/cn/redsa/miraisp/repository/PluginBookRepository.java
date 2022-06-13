package cn.redsa.miraisp.repository;

import cn.redsa.miraisp.entity.PluginBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PluginBookRepository extends JpaRepository<PluginBook, Long> {
    PluginBook findPluginBookByPluginId(String Id);
}
