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

import id.co.aio.procure_to_pay.avm2.TlTrRegal;


/**
 * ServiceImpl object for domain model class TlTrRegal.
 *
 * @see TlTrRegal
 */
@Service("AVM2.TlTrRegalService")
@Validated
@EntityService(entityClass = TlTrRegal.class, serviceId = "AVM2")
public class TlTrRegalServiceImpl implements TlTrRegalService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlTrRegalServiceImpl.class);


    @Autowired
    @Qualifier("AVM2.TlTrRegalDao")
    private WMGenericDao<TlTrRegal, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TlTrRegal, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlTrRegal create(TlTrRegal tlTrRegal) {
        LOGGER.debug("Creating a new TlTrRegal with information: {}", tlTrRegal);

        TlTrRegal tlTrRegalCreated = this.wmGenericDao.create(tlTrRegal);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tlTrRegalCreated);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlTrRegal getById(Integer tltrregalId) {
        LOGGER.debug("Finding TlTrRegal by id: {}", tltrregalId);
        return this.wmGenericDao.findById(tltrregalId);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlTrRegal findById(Integer tltrregalId) {
        LOGGER.debug("Finding TlTrRegal by id: {}", tltrregalId);
        try {
            return this.wmGenericDao.findById(tltrregalId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TlTrRegal found with id: {}", tltrregalId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public List<TlTrRegal> findByMultipleIds(List<Integer> tltrregalIds, boolean orderedReturn) {
        LOGGER.debug("Finding TlTrRegals by ids: {}", tltrregalIds);

        return this.wmGenericDao.findByMultipleIds(tltrregalIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "AVM2TransactionManager")
    @Override
    public TlTrRegal update(TlTrRegal tlTrRegal) {
        LOGGER.debug("Updating TlTrRegal with information: {}", tlTrRegal);

        this.wmGenericDao.update(tlTrRegal);
        this.wmGenericDao.refresh(tlTrRegal);

        return tlTrRegal;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlTrRegal partialUpdate(Integer tltrregalId, Map<String, Object>tlTrRegalPatch) {
        LOGGER.debug("Partially Updating the TlTrRegal with id: {}", tltrregalId);

        TlTrRegal tlTrRegal = getById(tltrregalId);

        try {
            ObjectReader tlTrRegalReader = this.objectMapper.reader().forType(TlTrRegal.class).withValueToUpdate(tlTrRegal);
            tlTrRegal = tlTrRegalReader.readValue(this.objectMapper.writeValueAsString(tlTrRegalPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tlTrRegalPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tlTrRegal = update(tlTrRegal);

        return tlTrRegal;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlTrRegal delete(Integer tltrregalId) {
        LOGGER.debug("Deleting TlTrRegal with id: {}", tltrregalId);
        TlTrRegal deleted = this.wmGenericDao.findById(tltrregalId);
        if (deleted == null) {
            LOGGER.debug("No TlTrRegal found with id: {}", tltrregalId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TlTrRegal.class.getSimpleName(), tltrregalId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public void delete(TlTrRegal tlTrRegal) {
        LOGGER.debug("Deleting TlTrRegal with {}", tlTrRegal);
        this.wmGenericDao.delete(tlTrRegal);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlTrRegal> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TlTrRegals");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlTrRegal> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TlTrRegals");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service AVM2 for table TlTrRegal to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service AVM2 for table TlTrRegal to {} format", options.getExportType());
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