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

import id.co.aio.procure_to_pay.avm2.TlMsTa;


/**
 * ServiceImpl object for domain model class TlMsTa.
 *
 * @see TlMsTa
 */
@Service("AVM2.TlMsTaService")
@Validated
@EntityService(entityClass = TlMsTa.class, serviceId = "AVM2")
public class TlMsTaServiceImpl implements TlMsTaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlMsTaServiceImpl.class);


    @Autowired
    @Qualifier("AVM2.TlMsTaDao")
    private WMGenericDao<TlMsTa, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TlMsTa, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsTa create(TlMsTa tlMsTa) {
        LOGGER.debug("Creating a new TlMsTa with information: {}", tlMsTa);

        TlMsTa tlMsTaCreated = this.wmGenericDao.create(tlMsTa);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tlMsTaCreated);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsTa getById(Integer tlmstaId) {
        LOGGER.debug("Finding TlMsTa by id: {}", tlmstaId);
        return this.wmGenericDao.findById(tlmstaId);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsTa findById(Integer tlmstaId) {
        LOGGER.debug("Finding TlMsTa by id: {}", tlmstaId);
        try {
            return this.wmGenericDao.findById(tlmstaId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TlMsTa found with id: {}", tlmstaId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public List<TlMsTa> findByMultipleIds(List<Integer> tlmstaIds, boolean orderedReturn) {
        LOGGER.debug("Finding TlMsTas by ids: {}", tlmstaIds);

        return this.wmGenericDao.findByMultipleIds(tlmstaIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "AVM2TransactionManager")
    @Override
    public TlMsTa update(TlMsTa tlMsTa) {
        LOGGER.debug("Updating TlMsTa with information: {}", tlMsTa);

        this.wmGenericDao.update(tlMsTa);
        this.wmGenericDao.refresh(tlMsTa);

        return tlMsTa;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsTa partialUpdate(Integer tlmstaId, Map<String, Object>tlMsTaPatch) {
        LOGGER.debug("Partially Updating the TlMsTa with id: {}", tlmstaId);

        TlMsTa tlMsTa = getById(tlmstaId);

        try {
            ObjectReader tlMsTaReader = this.objectMapper.reader().forType(TlMsTa.class).withValueToUpdate(tlMsTa);
            tlMsTa = tlMsTaReader.readValue(this.objectMapper.writeValueAsString(tlMsTaPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tlMsTaPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tlMsTa = update(tlMsTa);

        return tlMsTa;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsTa delete(Integer tlmstaId) {
        LOGGER.debug("Deleting TlMsTa with id: {}", tlmstaId);
        TlMsTa deleted = this.wmGenericDao.findById(tlmstaId);
        if (deleted == null) {
            LOGGER.debug("No TlMsTa found with id: {}", tlmstaId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TlMsTa.class.getSimpleName(), tlmstaId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public void delete(TlMsTa tlMsTa) {
        LOGGER.debug("Deleting TlMsTa with {}", tlMsTa);
        this.wmGenericDao.delete(tlMsTa);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlMsTa> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TlMsTas");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlMsTa> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TlMsTas");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service AVM2 for table TlMsTa to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service AVM2 for table TlMsTa to {} format", options.getExportType());
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