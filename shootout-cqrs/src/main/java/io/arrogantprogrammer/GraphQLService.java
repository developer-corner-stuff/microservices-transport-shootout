package io.arrogantprogrammer;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@GraphQLApi
public class GraphQLService {

    static final Logger LOGGER = LoggerFactory.getLogger(GraphQLService.class);

    @Query("allGreetings")
    @Description("Get all greetings")
    public List<Greeting> allGreetings() {
        return Greeting.listAll();
    }
}
