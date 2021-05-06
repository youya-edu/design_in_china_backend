package org.dic.demo.composition.database;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CompositionDao {

  List<DatabaseComposition> getAllCompositions();

  List<DatabaseProduct> getAllProducts();

  boolean compositionExists();

  int createCompositions(@Param("compositions") List<DatabaseComposition> compositions);

  int createProducts(@Param("products") List<DatabaseProduct> products);
}
