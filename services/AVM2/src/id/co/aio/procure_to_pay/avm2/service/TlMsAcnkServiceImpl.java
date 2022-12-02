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

import id.co.aio.procure_to_pay.avm2.TlMsAcnk;


/**
 * ServiceImpl object for domain model class TlMsAcnk.
 *
 * @see TlMsAcnk
 */
@Service("AVM2.TlMsAcnkService")
@Validated
@EntityService(entityClass = TlMsAcnk.class, serviceId = "AVM2")
public class TlMsAcnkServiceImpl implements TlMsAcnkService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlMsAcnkServiceImpl.class);


    @Autowired
    @Qualifier("AVM2.TlMsAcnkDao")
    private WMGenericDao<TlMsAcnk, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TlMsAcnk, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsAcnk create(TlMsAcnk tlMsAcnk) {
        LOGGER.debug("Creating a new TlMsAcnk with information: {}", tlMsAcnk);

        TlMsAcnk tlMsAcnkCreated = this.wmGenericDao.create(tlMsAcnk);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tlMsAcnkCreated);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsAcnk getById(Integer tlmsacnkId) {
        LOGGER.debug("Finding TlMsAcnk by id: {}", tlmsacnkId);
        return this.wmGenericDao.findById(tlmsacnkId);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsAcnk findById(Integer tlmsacnkId) {
        LOGGER.debug("Finding TlMsAcnk by id: {}", tlmsacnkId);
        try {
            return this.wmGenericDao.findById(tlmsacnkId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TlMsAcnk found with id: {}", tlmsacnkId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public List<TlMsAcnk> findByMultipleIds(List<Integer> tlmsacnkIds, boolean orderedReturn) {
        LOGGER.debug("Finding TlMsAcnks by ids: {}", tlmsacnkIds);

        return this.wmGenericDao.findByMultipleIds(tlmsacnkIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "AVM2TransactionManager")
    @Override
    public TlMsAcnk update(TlMsAcnk tlMsAcnk) {
        LOGGER.debug("Updating TlMsAcnk with information: {}", tlMsAcnk);

        this.wmGenericDao.update(tlMsAcnk);
        this.wmGenericDao.refresh(tlMsAcnk);

        return tlMsAcnk;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsAcnk partialUpdate(Integer tlmsacnkId, Map<String, Object>tlMsAcnkPatch) {
        LOGGER.debug("Partially Updating the TlMsAcnk with id: {}", tlmsacnkId);

        TlMsAcnk tlMsAcnk = getById(tlmsacnkId);

        try {
            ObjectReader tlMsAcnkReader = this.objectMapper.reader().forType(TlMsAcnk.class).withValueToUpdate(tlMsAcnk);
            tlMsAcnk = tlMsAcnkReader.readValue(this.objectMapper.writeValueAsString(tlMsAcnkPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tlMsAcnkPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tlMsAcnk = update(tlMsAcnk);

        return tlMsAcnk;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsAcnk delete(Integer tlmsacnkId) {
        LOGGER.debug("Deleting TlMsAcnk with id: {}", tlmsacnkId);
        TlMsAcnk deleted = this.wmGenericDao.findById(tlmsacnkId);
        if (deleted == null) {
            LOGGER.debug("No TlMsAcnk found with id: {}", tlmsacnkId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TlMsAcnk.class.getSimpleName(), tlmsacnkId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public void delete(TlMsAcnk tlMsAcnk) {
        LOGGER.debug("Deleting TlMsAcnk with {}", tlMsAcnk);
        this.wmGenericDao.delete(tlMsAcnk);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlMsAcnk> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TlMsAcnks");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlMsAcnk> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TlMsAcnks");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service AVM2 for table TlMsAcnk to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service AVM2 for table TlMsAcnk to {} format", options.getExportType());
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