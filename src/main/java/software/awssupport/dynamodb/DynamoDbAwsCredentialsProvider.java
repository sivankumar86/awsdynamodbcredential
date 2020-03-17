package software.awssupport.dynamodb;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.STSAssumeRoleSessionCredentialsProvider;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;


public class DynamoDbAwsCredentialsProvider implements AWSCredentialsProvider, Configurable {

    private static final String roleArnKey = "assumed.creds.role.arn";
    private static final String roleSessionKey = "assumed.creds.session.name";

    private   String roleArn="";
    private   String session="";
    private Configuration configuration;
    private AWSCredentialsProvider provider;

    private void init() {
        roleArn = configuration.get(roleArnKey);
        session = configuration.get(roleSessionKey) == null ? "default" : configuration.get(roleSessionKey);
        if (StringUtils.isEmpty(roleArn))
            throw new IllegalStateException(String.format("Please set %s before use.", roleArnKey));
    }

    @Override
    public AWSCredentials getCredentials() {
        return this.getAssumeRoleSessionCredentialsProvider(roleArn, session);
    }

    @Override
    public void refresh() {

    }

    @Override
    public Configuration getConf() {
        return this.configuration;
    }

    @Override
    public void setConf(Configuration configuration) {
        this.configuration = configuration;

        init();
    }

    protected AWSCredentials getAssumeRoleSessionCredentialsProvider(final String roleArn, final String roleSession) {
        AWSCredentialsProvider credentialsProvider= new STSAssumeRoleSessionCredentialsProvider.Builder(roleArn, roleSession).build();
      return credentialsProvider.getCredentials();
    }
}

