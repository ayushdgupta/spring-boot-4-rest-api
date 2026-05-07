### REDIS Cache

```markdown
// commands to install redis-cli in local windows

curl -fsSL https://packages.redis.io/gpg | sudo gpg --dearmor -o /usr/share/keyrings/redis-archive-keyring.gpg    

echo "deb [signed-by=/usr/share/keyrings/redis-archive-keyring.gpg] https://packages.redis.io/deb $(lsb_release -cs) main" | sudo tee /etc/apt/sources.list.d/redis.list    

sudo apt-get update    
sudo apt-get install redis    

sudo service redis-server start    

redis-cli
```

```markdown
// commands to run redis-cli in wsl system command prompt
ping  --> response should be pong    
get CACHE_NAME::CACHE_KEY ---> to print values stored on cache_key    
keys * ---> to print all cache data  
```

### Spring-Security

## Basic Architecture
![Basic security architecture with default username password](src/main/resources/spring-security.png)

## Config for in-memory users
```yaml
@Bean
public UserDetailsService userDetailsService(){

    UserDetails userDetailsOne = User
            .withUsername(userOne)
            .password(passwordEncoder().encode(userOnePassword))
            .roles(UserRoles.ROLE_ADMIN.getRole())
            .build();

    UserDetails userDetailsTwo = User
            .withUsername(userTwo)
            .password(passwordEncoder().encode(userTwoPassword))
            .roles(UserRoles.ROLE_USER.getRole())
            .build();

    return new InMemoryUserDetailsManager(userDetailsOne, userDetailsTwo);
}
```