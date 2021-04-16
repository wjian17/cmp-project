use #{auth};

INSERT INTO oauth_client_details VALUES ('auth', 'auth', '{noop}auth', 'all', 'refresh_token,authorization_code,refresh_code,password,client_credentials', 'http://www.baidu.com', 'ROLE_PROJECT_ADMIN', 7200, 1800, NULL, 'true');
INSERT INTO oauth_client_details VALUES ('auth-client', 'auth-client', '{noop}auth-client', 'all', 'refresh_token,authorization_code,refresh_code,password,client_credentials', 'http://www.baidu.com', 'ROLE_PROJECT_ADMIN', 7200, 1800, NULL, 'true');
INSERT INTO oauth_client_details VALUES ('rest', 'rest', '{noop}rest', 'all', 'refresh_token,authorization_code,refresh_code,password,client_credentials', 'http://www.baidu.com', 'ROLE_PROJECT_ADMIN', 7200, 1800, NULL, 'true');