package org.dic.demo.composition.database;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.dic.demo.composition.model.CompositionStatus;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/** Bean for providing data for development. Never use this in production...!! */
@Profile("dev")
@Component
@DependsOn("flywayInitializer")
@AllArgsConstructor
public class DataProvider {
  private final CompositionDao compositionDao;

  @PostConstruct
  private void addCompositionData() {
    if (compositionDao.compositionExists()) {
      return;
    }

    Random random = new Random();
    List<DatabaseComposition> compositions =
        IntStream.range(0, 500)
            .mapToObj(
                idx -> {
                  Date now = new Date();
                  return DatabaseComposition.builder()
                      .authorId(1)
                      .name(String.format("作品%d", idx))
                      .description("这是一个测试用的作品")
                      .image("test.png")
                      .status(CompositionStatus.PUBLIC.name())
                      .createdAt(now)
                      .lastModified(now)
                      .issuedAt(now)
                      .forSale(true)
                      .price(BigDecimal.valueOf(random.nextDouble() * 1000))
                      .stock(random.nextInt(10))
                      .build();
                })
            .collect(Collectors.toList());
    compositionDao.createCompositions(compositions);
    compositionDao.createProducts(compositions);
  }
}
