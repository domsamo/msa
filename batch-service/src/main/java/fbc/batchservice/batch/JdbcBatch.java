package fbc.batchservice.batch;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.batchservice.batch
 * @fileName : JdbcBatch
 * @date : 24. 12. 4.
 * @description : JDBC 배치 샘플
 * ===========================================================
 * https://github.com/spring-projects/spring-batch/tree/main/spring-batch-samples/src/main/java/org/springframework/batch/samples
 *
 */
@Configuration
public class JdbcBatch {

//    @Bean
//    public JdbcCursorItemReader<CustomerCredit> itemReader(DataSource dataSource) {
//        String sql = "select ID, NAME, CREDIT from CUSTOMER";
//        return new JdbcCursorItemReaderBuilder<CustomerCredit>().name("customerReader")
//                .dataSource(dataSource)
//                .sql(sql)
//                .rowMapper(new CustomerCreditRowMapper())
//                .build();
//    }

//    @Bean
//    @StepScope
//    public JdbcPagingItemReader<CustomerCredit> itemReader(DataSource dataSource,
//                                                           @Value("#{jobParameters['credit']}") Double credit) {
//        Map<String, Object> parameterValues = new HashMap<>();
//        parameterValues.put("statusCode", "PE");
//        parameterValues.put("credit", credit);
//        parameterValues.put("type", "COLLECTION");
//
//        return new JdbcPagingItemReaderBuilder<CustomerCredit>().name("customerReader")
//                .dataSource(dataSource)
//                .selectClause("select NAME, ID, CREDIT")
//                .fromClause("FROM CUSTOMER")
//                .whereClause("WHERE CREDIT > :credit")
//                .sortKeys(Map.of("ID", Order.ASCENDING))
//                .rowMapper(new CustomerCreditRowMapper())
//                .pageSize(2)
//                .parameterValues(parameterValues)
//                .build();
//    }
}
