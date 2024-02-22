package com.example.config;

import bpd.bt.utility.EncryptionHelper;
import com.example.utils.BeyondTrustUtil;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Esta clase permite la sobreescritura de las properties 'passwords' que se
 * deben obtener desde el BT
 *
 */
public class ProcessEncryptedConfigSource implements ConfigSource {
    private static final Logger LOG = LoggerFactory.getLogger(ProcessEncryptedConfigSource.class);

    private static final Map<String, String> configuration = new HashMap<>();
    private static final String CERTIFICATES = "quarkus.certificate.file.url";

    private static final String QUARKUS_BT = "quarkus.bt.";

    private static final List<DataBt> PROPS = new ArrayList<>();

    private static void consumer(DataBt key) {
        ConfigValue configValuePass = ConfigProvider.getConfig().getConfigValue(key.pass);
        ConfigValue configValueUser = ConfigProvider.getConfig().getConfigValue(key.user);
        ConfigValue configValueSys = ConfigProvider.getConfig().getConfigValue(key.sys);
        ConfigValue configValueType = ConfigProvider.getConfig().getConfigValue(key.type);
        if (configValueUser.getSourceName() != null) {
            String decryptUser = EncryptionHelper.decrypt(configValueUser.getValue());
            String user = decryptUser != null ? decryptUser : configValueUser.getValue();
            String sys = configValueSys.getValue();
            String type = configValueType.getValue();
            Optional<String> passwordOptional = BeyondTrustUtil.getDBPasswordFromBT(user, sys, type);
            if (passwordOptional.isPresent()) {
                configuration.put(key.pass, passwordOptional.get());
                configuration.put(key.user, user);
                configuration.put(key.type, type);
            } else {
                String decryptPass = EncryptionHelper.decrypt(configValuePass.getValue());
                String pass = decryptPass != null ? decryptPass : configValuePass.getValue();
                configuration.put(key.pass, pass);
                configuration.put(key.user, user);
                configuration.put(key.type, type);
            }
        }
    }

    static class DataBt {

        String user;
        String pass;
        String sys;
        String type;

        public DataBt(String user, String pass, String sys, String type) {
            this.user = user;
            this.pass = pass;
            this.sys = sys;
            this.type = type;
        }
    }

    static {
        ConfigValue certificatePath = ConfigProvider.getConfig().getConfigValue(CERTIFICATES);
        if (certificatePath.getValue() != null) {
            Path path = Paths.get(certificatePath.getValue());
            System.setProperty("javax.net.ssl.trustStore", path.toString());
            System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
            System.setProperty("javax.net.ssl.keyStore", path.toString());
            System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
        }

        Properties prop = null;
        InputStream is = null;
        try {
            // Getting the properties
            prop = new Properties();
            is = ProcessEncryptedConfigSource.class.getResourceAsStream("/application.properties");
            prop.load(is);

            // I load a list of string with only the keys (without the value)
            List<String> keyNames = new ArrayList<>();
            prop.keySet().forEach(o -> keyNames.add(o.toString()));

            // Filter leaving only the BT ones
            List<String> excludedKeys = List.of(
                    "quarkus.bt.baseurl", "quarkus.bt.apikey", "quarkus.bt.runasuser",
                    "quarkus.bt.systemname", "quarkus.bt.accountname", "quarkus.bt.type"
            );
            List<String> filteredKeys = keyNames.stream().filter(
                    k -> k.startsWith(QUARKUS_BT) && excludedKeys.stream().noneMatch(k::contains)
            ).collect(Collectors.toList());

            // I order the result
            Collections.sort(filteredKeys);

            // I get the values for the invocations to BT
            String systemName = "";
            String accountName = "";
            String passwordName = "";
            String typeName = "";
            for (String k : filteredKeys) {
                String node = k.substring(k.lastIndexOf(".") + 1);
                if (node.equals("systemname")) {
                    systemName = k;
                } else if (node.equals("accountname")) {
                    accountName = k;
                } else if (node.equals("password")) {
                    passwordName = k;
                } else {
                    typeName = k;
                }
                if (!systemName.isEmpty() && !accountName.isEmpty() && !typeName.isEmpty()) {
                    PROPS.add(new DataBt(accountName, passwordName, systemName, typeName));
                    accountName = "";
                    systemName = "";
                    typeName = "";
                }
            }
            PROPS.stream().forEach(ProcessEncryptedConfigSource::consumer);

        } catch (FileNotFoundException e) {
            LOG.error("Error reading properties file: {}", e.getMessage(), e);
        } catch (IOException e) {
            LOG.error("Error in properties file: {}", e.getMessage(), e);
        }
    }

    @Override
    public Set<String> getPropertyNames() {
        return configuration.keySet();
    }

    @Override
    public int getOrdinal() {
        return 500;
    }

    @Override
    public String getValue(String propertyName) {
        return configuration.get(propertyName);
    }

    @Override
    public String getName() {
        return ProcessEncryptedConfigSource.class.getSimpleName();
    }

}
