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

import id.co.aio.procure_to_pay.avm2.VwKontrakOri;


/**
 * ServiceImpl object for domain model class VwKontrakOri.
 *
 * @see VwKontrakOri
 */
@Service("AVM2.VwKontrakOriService")
@Validated
@EntityService(entityClass = VwKontrakOri.class, serviceId = "AVM2")
public class VwKontrakOriServiceImpl implements VwKontrakOriService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VwKontrakOriServiceImpl.class);


    @Autowired
    @Qualifier("AVM2.VwKontrakOriDao")
    private WMGenericDao<VwKontrakOri, String> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<VwKontrakOri, String> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public VwKontrakOri create(VwKontrakOri vwKontrakOri) {
        LOGGER.debug("Creating a new VwKontrakOri with information: {}", vwKontrakOri);

        VwKontrakOri vwKontrakOriCreated = this.wmGenericDao.create(vwKontrakOri);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(vwKontrakOriCreated);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public VwKontrakOri getById(String vwkontrakoriId) {
        LOGGER.debug("Finding VwKontrakOri by id: {}", vwkontrakoriId);
        return this.wmGenericDao.findById(vwkontrakoriId);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public VwKontrakOri findById(String vwkontrakoriId) {
        LOGGER.debug("Finding VwKontrakOri by id: {}", vwkontrakoriId);
        try {
            return this.wmGenericDao.findById(vwkontrakoriId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No VwKontrakOri found with id: {}", vwkontrakoriId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public List<VwKontrakOri> findByMultipleIds(List<String> vwkontrakoriIds, boolean orderedReturn) {
        LOGGER.debug("Finding VwKontrakOris by ids: {}", vwkontrakoriIds);

        return this.wmGenericDao.findByMultipleIds(vwkontrakoriIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "AVM2TransactionManager")
    @Override
    public VwKontrakOri update(VwKontrakOri vwKontrakOri) {
        LOGGER.debug("Updating VwKontrakOri with information: {}", vwKontrakOri);

        this.wmGenericDao.update(vwKontrakOri);
        this.wmGenericDao.refresh(vwKontrakOri);

        return vwKontrakOri;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public VwKontrakOri partialUpdate(String vwkontrakoriId, Map<String, Object>vwKontrakOriPatch) {
        LOGGER.debug("Partially Updating the VwKontrakOri with id: {}", vwkontrakoriId);

        VwKontrakOri vwKontrakOri = getById(vwkontrakoriId);

        try {
            ObjectReader vwKontrakOriReader = this.objectMapper.reader().forType(VwKontrakOri.class).withValueToUpdate(vwKontrakOri);
            vwKontrakOri = vwKontrakOriReader.readValue(this.objectMapper.writeValueAsString(vwKontrakOriPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", vwKontrakOriPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        vwKontrakOri = update(vwKontrakOri);

        return vwKontrakOri;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public VwKontrakOri delete(String vwkontrakoriId) {
        LOGGER.debug("Deleting VwKontrakOri with id: {}", vwkontrakoriId);
        VwKontrakOri deleted = this.wmGenericDao.findById(vwkontrakoriId);
        if (deleted == null) {
            LOGGER.debug("No VwKontrakOri found with id: {}", vwkontrakoriId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), VwKontrakOri.class.getSimpleName(), vwkontrakoriId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public void delete(VwKontrakOri vwKontrakOri) {
        LOGGER.debug("Deleting VwKontrakOri with {}", vwKontrakOri);
        this.wmGenericDao.delete(vwKontrakOri);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<VwKontrakOri> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all VwKontrakOris");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<VwKontrakOri> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all VwKontrakOris");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service AVM2 for table VwKontrakOri to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service AVM2 for table VwKontrakOri to {} format", options.getExportType());
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