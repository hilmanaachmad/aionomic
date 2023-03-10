/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_xspeed_user.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.wavemaker.commons.InvalidInputException;
import com.wavemaker.commons.MessageResource;
import com.wavemaker.runtime.data.annotations.EntityService;
import com.wavemaker.runtime.data.dao.WMGenericDao;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import id.co.aio.procure_to_pay.aio_xspeed_user.PhpMsLogin;


/**
 * ServiceImpl object for domain model class PhpMsLogin.
 *
 * @see PhpMsLogin
 */
@Service("aio_xspeed_user.PhpMsLoginService")
@Validated
@EntityService(entityClass = PhpMsLogin.class, serviceId = "aio_xspeed_user")
public class PhpMsLoginServiceImpl implements PhpMsLoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhpMsLoginServiceImpl.class);


    @Autowired
    @Qualifier("aio_xspeed_user.PhpMsLoginDao")
    private WMGenericDao<PhpMsLogin, String> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<PhpMsLogin, String> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_xspeed_userTransactionManager")
    @Override
    public PhpMsLogin create(PhpMsLogin phpMsLogin) {
        LOGGER.debug("Creating a new PhpMsLogin with information: {}", phpMsLogin);

        PhpMsLogin phpMsLoginCreated = this.wmGenericDao.create(phpMsLogin);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(phpMsLoginCreated);
    }

    @Transactional(readOnly = true, value = "aio_xspeed_userTransactionManager")
    @Override
    public PhpMsLogin getById(String phpmsloginId) {
        LOGGER.debug("Finding PhpMsLogin by id: {}", phpmsloginId);
        return this.wmGenericDao.findById(phpmsloginId);
    }

    @Transactional(readOnly = true, value = "aio_xspeed_userTransactionManager")
    @Override
    public PhpMsLogin findById(String phpmsloginId) {
        LOGGER.debug("Finding PhpMsLogin by id: {}", phpmsloginId);
        try {
            return this.wmGenericDao.findById(phpmsloginId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No PhpMsLogin found with id: {}", phpmsloginId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_xspeed_userTransactionManager")
    @Override
    public List<PhpMsLogin> findByMultipleIds(List<String> phpmsloginIds, boolean orderedReturn) {
        LOGGER.debug("Finding PhpMsLogins by ids: {}", phpmsloginIds);

        return this.wmGenericDao.findByMultipleIds(phpmsloginIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_xspeed_userTransactionManager")
    @Override
    public PhpMsLogin update(PhpMsLogin phpMsLogin) {
        LOGGER.debug("Updating PhpMsLogin with information: {}", phpMsLogin);

        this.wmGenericDao.update(phpMsLogin);
        this.wmGenericDao.refresh(phpMsLogin);

        return phpMsLogin;
    }

    @Transactional(value = "aio_xspeed_userTransactionManager")
    @Override
    public PhpMsLogin partialUpdate(String phpmsloginId, Map<String, Object>phpMsLoginPatch) {
        LOGGER.debug("Partially Updating the PhpMsLogin with id: {}", phpmsloginId);

        PhpMsLogin phpMsLogin = getById(phpmsloginId);

        try {
            ObjectReader phpMsLoginReader = this.objectMapper.reader().forType(PhpMsLogin.class).withValueToUpdate(phpMsLogin);
            phpMsLogin = phpMsLoginReader.readValue(this.objectMapper.writeValueAsString(phpMsLoginPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", phpMsLoginPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        phpMsLogin = update(phpMsLogin);

        return phpMsLogin;
    }

    @Transactional(value = "aio_xspeed_userTransactionManager")
    @Override
    public PhpMsLogin delete(String phpmsloginId) {
        LOGGER.debug("Deleting PhpMsLogin with id: {}", phpmsloginId);
        PhpMsLogin deleted = this.wmGenericDao.findById(phpmsloginId);
        if (deleted == null) {
            LOGGER.debug("No PhpMsLogin found with id: {}", phpmsloginId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), PhpMsLogin.class.getSimpleName(), phpmsloginId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_xspeed_userTransactionManager")
    @Override
    public void delete(PhpMsLogin phpMsLogin) {
        LOGGER.debug("Deleting PhpMsLogin with {}", phpMsLogin);
        this.wmGenericDao.delete(phpMsLogin);
    }

    @Transactional(readOnly = true, value = "aio_xspeed_userTransactionManager")
    @Override
    public Page<PhpMsLogin> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all PhpMsLogins");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_xspeed_userTransactionManager")
    @Override
    public Page<PhpMsLogin> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all PhpMsLogins");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_xspeed_userTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_xspeed_user for table PhpMsLogin to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_xspeed_userTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_xspeed_user for table PhpMsLogin to {} format", options.getExportType());
        this.wmGenericDao.export(options, pageable, outputStream);
    }

    @Transactional(readOnly = true, value = "aio_xspeed_userTransactionManager")
    @Override
    public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "aio_xspeed_userTransactionManager")
    @Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }



}