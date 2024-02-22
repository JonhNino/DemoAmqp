package com.example.utils;

import bpd.microservice.common.btutility.extension.runtime.BtApiService;
import bpd.microservice.common.btutility.extension.runtime.BtApiServiceConfiguration;
import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class BeyondTrustUtil {

    private static final Logger LOG = LoggerFactory.getLogger(BeyondTrustUtil.class);

    private BeyondTrustUtil() {
    }

    public static Optional<String> getDBPasswordFromBT(String accountName, String systemName, String btType) {
        String btPsAuthKey = ConfigProvider.getConfig().getConfigValue("quarkus.bt.apikey").getValue();
        String btRunAsUser = ConfigProvider.getConfig().getConfigValue("quarkus.bt.runasuser").getValue();
        String btBaseUrl = ConfigProvider.getConfig().getConfigValue("quarkus.bt.baseurl").getValue();
        Optional<String> password = Optional.empty();

        try {
            LOG.info("Getting DB password from BT for accountName : {}, systemname : {}", accountName, systemName);
            BtApiServiceConfiguration btApiServiceConfiguration = new BtApiServiceConfiguration();
            btApiServiceConfiguration.setBaseUrl(btBaseUrl);
            btApiServiceConfiguration.setAccountName(accountName);
            btApiServiceConfiguration.setRunAsUser(btRunAsUser);
            btApiServiceConfiguration.setApiKey(btPsAuthKey);
            btApiServiceConfiguration.setType(btType);
            btApiServiceConfiguration.setSystemName(systemName);
            BtApiService btApiService = new BtApiService(btApiServiceConfiguration);
            String btResp = btApiService.getPasswordOrKey();
            if (btResp != null && !btResp.isEmpty()) {
                password = Optional.ofNullable(btResp);
            }
        } catch (Exception e) {
            String msg = String.format("Unable to retrieve password for accountName '%s' from BeyondTrust. ",
                    accountName);
            LOG.error(msg, e);
        }
        return password;
    }
}

