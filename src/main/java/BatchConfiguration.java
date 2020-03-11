

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.example.demo.config.UserProcessor;

import com.example.demo.model.User;



@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

 @Autowired
 public JobBuilderFactory jobBuilderFactory;
 
 @Autowired
 public StepBuilderFactory stepBuilderFactory;

private DataSource dataSource;
 

 
 @Bean
 public FlatFileItemReader<User> reader(){
  FlatFileItemReader<User> reader = new FlatFileItemReader<User>();
  reader.setResource(new ClassPathResource("User.csv"));
  reader.setLineMapper(new DefaultLineMapper<User>() {{
   setLineTokenizer(new DelimitedLineTokenizer() {{
    setNames(new String[] { "firstName","middleName","lastName","phoneNumber","note" });
   }});
   setFieldSetMapper(new BeanWrapperFieldSetMapper<User>() {{
    setTargetType(User.class);
   }});
   
  }});
  
  return reader;
 }
 
 @Bean
 public UserProcessor processor(){
  return new UserProcessor();
 }
 
 @Bean
 public JdbcBatchItemWriter<User> writer(){
  JdbcBatchItemWriter<User> writer = new JdbcBatchItemWriter<User>();
  writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<User>());
  writer.setSql("INSERT INTO user(firstName,middleName,lastName,phoneNumber,note) VALUES (:firstName,:middleName,:lastName,:phoneNumber,:note)");
  writer.setDataSource(dataSource);
  
  return writer;
 }
 
 @Bean
 public Step step1() {
  return stepBuilderFactory.get("step1").<User, User> chunk(3)
    .reader(reader())
    .processor(processor())
    .writer(writer())
    .build();
 }
 
 @Bean
 public Job importUserJob() {
  return jobBuilderFactory.get("importUserJob")
    .incrementer(new RunIdIncrementer())
    .flow(step1())
    .end()
    .build();
 }
}

 