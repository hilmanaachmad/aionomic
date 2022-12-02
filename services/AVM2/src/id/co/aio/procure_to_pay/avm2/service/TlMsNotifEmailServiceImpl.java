/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2.service;

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

import id.co.aio.procure_to_pay.avm2.TlMsNotifEmail;


/**
 * ServiceImpl object for domain model class TlMsNotifEmail.
 *
 * @see TlMsNotifEmail
 */
@Service("AVM2.TlMsNotifEmailService")
@Validated
@EntityService(entityClass = TlMsNotifEmail.class, serviceId = "AVM2")
public class TlMsNotifEmailServiceImpl implements TlMsNotifEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlMsNotifEmailServiceImpl.class);


    @Autowired
    @Qualifier("AVM2.TlMsNotifEmailDao")
    private WMGenericDao<TlMsNotifEmail, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TlMsNotifEmail, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsNotifEmail create(TlMsNotifEmail tlMsNotifEmail) {
        LOGGER.debug("Creating a new TlMsNotifEmail with information: {}", tlMsNotifEmail);

        TlMsNotifEmail tlMsNotifEmailCreated = this.wmGenericDao.create(tlMsNotifEmail);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tlMsNotifEmailCreated);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsNotifEmail getById(Integer tlmsnotifemailId) {
        LOGGER.debug("Finding TlMsNotifEmail by id: {}", tlmsnotifemailId);
        return this.wmGenericDao.findById(tlmsnotifemailId);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsNotifEmail findById(Integer tlmsnotifemailId) {
        LOGGER.debug("Finding TlMsNotifEmail by id: {}", tlmsnotifemailId);
        try {
            return this.wmGenericDao.findById(tlmsnotifemailId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TlMsNotifEmail found with id: {}", tlmsnotifemailId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public List<TlMsNotifEmail> findByMultipleIds(List<Integer> tlmsnotifemailIds, boolean orderedReturn) {
        LOGGER.debug("Finding TlMsNotifEmails by ids: {}", tlmsnotifemailIds);

        return this.wmGenericDao.findByMultipleIds(tlmsnotifemailIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "AVM2TransactionManager")
    @Override
    public TlMsNotifEmail update(TlMsNotifEmail tlMsNotifEmail) {
        LOGGER.debug("Updating TlMsNotifEmail with information: {}", tlMsNotifEmail);

        this.wmGenericDao.update(tlMsNotifEmail);
        this.wmGenericDao.refresh(tlMsNotifEmail);

        return tlMsNotifEmail;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsNotifEmail partialUpdate(Integer tlmsnotifemailId, Map<String, Object>tlMsNotifEmailPatch) {
        LOGGER.debug("Partially Updating the TlMsNotifEmail with id: {}", tlmsnotifemailId);

        TlMsNotifEmail tlMsNotifEmail = getById(tlmsnotifemailId);

        try {
            ObjectReader tlMsNotifEmailReader = this.objectMapper.reader().forType(TlMsNotifEmail.class).withValueToUpdate(tlMsNotifEmail);
            tlMsNotifEmail = tlMsNotifEmailReader.readValue(this.objectMapper.writeValueAsString(tlMsNotifEmailPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tlMsNotifEmailPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tlMsNotifEmail = update(tlMsNotifEmail);

        return tlMsNotifEmail;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsNotifEmail delete(Integer tlmsnotifemailId) {
        LOGGER.debug("Deleting TlMsNotifEmail with id: {}", tlmsnotifemailId);
        TlMsNotifEmail deleted = this.wmGenericDao.findById(tlmsnotifemailId);
        if (deleted == null) {
            LOGGER.debug("No TlMsNotifEmail found with id: {}", tlmsnotifemailId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TlMsNotifEmail.class.getSimpleName(), tlmsnotifemailId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public void delete(TlMsNotifEmail tlMsNotifEmail) {
        LOGGER.debug("Deleting TlMsNotifEmail with {}", tlMsNotifEmail);
        this.wmGenericDao.delete(tlMsNotifEmail);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlMsNotifEmail> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TlMsNotifEmails");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlMsNotifEmail> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TlMsNotifEmails");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service AVM2 for table TlMsNotifEmail to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service AVM2 for table TlMsNotifEmail to {} format", options.getExportType());
        this.wmGenericDao.export(options, pageable, outputStream);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }



}