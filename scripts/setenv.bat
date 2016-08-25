rem If the JRE which configured with environment variables does not satisfy the minimal requirement of this app,
rem change the _JRE_HOME variable to a fixed path.

set JRE_HOME=%JAVA_HOME%


set TCP_LISTEN=0.0.0.0
set TCP_PORT=8000

rem More arguments passed to spring boot or jvm
set EXTRA_ARGS=--proxy.name=apps.sparksoft.com.cn --proxy.port=443