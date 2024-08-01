package example;

import java.util.List;
import java.util.function.Function;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.ListTablesRequest;
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse;

@SpringBootApplication
public class FunctionConfiguration {

	/*
	 * You need this main method (empty) or explicit <start-class>example.FunctionConfiguration</start-class>
	 * in the POM to ensure boot plug-in makes the correct entry
	 */
	public static void main(String[] args) {
		// empty unless using Custom runtime at which point it should include
		// SpringApplication.run(FunctionConfiguration.class, args);
	}

	@Bean
	public Function<String, String> uppercase() {
		return value -> value.toUpperCase();
	}

	@Bean
	public Function<String, String> dynamoSample() {
		Region region = Region.AP_NORTHEAST_2;
		DynamoDbClient ddb = DynamoDbClient.builder()
			.credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create("accessKey", "secretKey")))
										   .region(region)
										   .build();
		listAllTables(ddb);
		ddb.close();
		return value -> uppercase().apply(value) + " from DynamoDB";
	}

	public static void listAllTables(DynamoDbClient ddb) {
		boolean moreTables = true;
		String lastName = null;

		while (moreTables) {
			try {
				ListTablesResponse response = null;
				if (lastName == null) {
					ListTablesRequest request = ListTablesRequest.builder().build();
					response = ddb.listTables(request);
				} else {
					ListTablesRequest request = ListTablesRequest.builder()
																 .exclusiveStartTableName(lastName).build();
					response = ddb.listTables(request);
				}

				List<String> tableNames = response.tableNames();
				if (tableNames.size() > 0) {
					for (String curName : tableNames) {
						System.out.format("* %s\n", curName);
					}
				} else {
					System.out.println("No tables found!");
					System.exit(0);
				}

				lastName = response.lastEvaluatedTableName();
				if (lastName == null) {
					moreTables = false;
				}

			} catch (DynamoDbException e) {
				System.err.println(e.getMessage());
				System.exit(1);
			}
		}
		System.out.println("\nDone!");
	}

}
