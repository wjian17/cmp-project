use #{auth};

INSERT INTO oauth_client_details VALUES ('auth', 'resourceId', '{noop}auth', 'all', 'refresh_token,authorization_code,refresh_code,password,client_credentials', 'http://www.baidu.com', 'ROLE_PROJECT_ADMIN', 7200, 1800, NULL, 'true');
INSERT INTO oauth_client_details VALUES ('auth-client', 'resourceId', '{noop}auth-client', 'all', 'refresh_token,authorization_code,refresh_code,password,client_credentials', 'http://www.baidu.com', 'ROLE_PROJECT_ADMIN', 7200, 1800, NULL, 'true');