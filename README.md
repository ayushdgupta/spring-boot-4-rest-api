### REDIS Cache
// commands to install redis-cli in local windows

curl -fsSL https://packages.redis.io/gpg | sudo gpg --dearmor -o /usr/share/keyrings/redis-archive-keyring.gpg    

echo "deb [signed-by=/usr/share/keyrings/redis-archive-keyring.gpg] https://packages.redis.io/deb $(lsb_release -cs) main" | sudo tee /etc/apt/sources.list.d/redis.list    

sudo apt-get update    
sudo apt-get install redis    

sudo service redis-server start    

redis-cli

// commands to run redis-cli in wsl system command prompt
ping  --> response should be pong    
get CACHE_NAME::CACHE_KEY ---> to pring value stored on cache_key    
keys * ---> to print all cache data    