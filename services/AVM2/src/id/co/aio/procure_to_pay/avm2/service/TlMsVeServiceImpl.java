/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
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

import id.co.aio.procure_to_pay.avm2.TlMsVe;


/**
 * ServiceImpl object for domain model class TlMsVe.
 *
 * @see TlMsVe
 */
@Service("AVM2.TlMsVeService")
@Validated
@EntityService(entityClass = TlMsVe.class, serviceId = "AVM2")
public class TlMsVeServiceImpl implements TlMsVeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlMsVeServiceImpl.class);


    @Autowired
    @Qualifier("AVM2.TlMsVeDao")
    private WMGenericDao<TlMsVe, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TlMsVe, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsVe create(TlMsVe tlMsVe) {
        LOGGER.debug("Creating a new TlMsVe with information: {}", tlMsVe);

        TlMsVe tlMsVeCreated = this.wmGenericDao.create(tlMsVe);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tlMsVeCreated);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsVe getById(Integer tlmsveId) {
        LOGGER.debug("Finding TlMsVe by id: {}", tlmsveId);
        return this.wmGenericDao.findById(tlmsveId);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsVe findById(Integer tlmsveId) {
        LOGGER.debug("Finding TlMsVe by id: {}", tlmsveId);
        try {
            return this.wmGenericDao.findById(tlmsveId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TlMsVe found with id: {}", tlmsveId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public List<TlMsVe> findByMultipleIds(List<Integer> tlmsveIds, boolean orderedReturn) {
        LOGGER.debug("Finding TlMsVes by ids: {}", tlmsveIds);

        return this.wmGenericDao.findByMultipleIds(tlmsveIds, orderedReturn);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsVe getByAbVsi(Integer abVsi) {
        Map<String, Object> abVsiMap = new HashMap<>();
        abVsiMap.put("abVsi", abVsi);

        LOGGER.debug("Finding TlMsVe by unique keys: {}", abVsiMap);
        return this.wmGenericDao.findByUniqueKey(abVsiMap);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class, value = "AVM2TransactionManager")
    @Override
    public TlMsVe update(TlMsVe tlMsVe) {
        LOGGER.debug("Updating TlMsVe with information: {}", tlMsVe);

        this.wmGenericDao.update(tlMsVe);
        this.wmGenericDao.refresh(tlMsVe);

        return tlMsVe;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsVe partialUpdate(Integer tlmsveId, Map<String, Object>tlMsVePatch) {
        LOGGER.debug("Partially Updating the TlMsVe with id: {}", tlmsveId);

        TlMsVe tlMsVe = getById(tlmsveId);

        try {
            ObjectReader tlMsVeReader = this.objectMapper.reader().forType(TlMsVe.class).withValueToUpdate(tlMsVe);
            tlMsVe = tlMsVeReader.readValue(this.objectMapper.writeValueAsString(tlMsVePatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tlMsVePatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tlMsVe = update(tlMsVe);

        return tlMsVe;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsVe delete(Integer tlmsveId) {
        LOGGER.debug("Deleting TlMsVe with id: {}", tlmsveId);
        TlMsVe deleted = this.wmGenericDao.findById(tlmsveId);
        if (deleted == null) {
            LOGGER.debug("No TlMsVe found with id: {}", tlmsveId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TlMsVe.class.getSimpleName(), tlmsveId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public void delete(TlMsVe tlMsVe) {
        LOGGER.debug("Deleting TlMsVe with {}", tlMsVe);
        this.wmGenericDao.delete(tlMsVe);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlMsVe> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TlMsVes");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlMsVe> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TlMsVes");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service AVM2 for table TlMsVe to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service AVM2 for table TlMsVe to {} format", options.getExportType());
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