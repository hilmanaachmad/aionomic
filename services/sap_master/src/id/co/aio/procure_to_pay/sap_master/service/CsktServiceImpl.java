/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.sap_master.service;

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

import id.co.aio.procure_to_pay.sap_master.Cskt;
import id.co.aio.procure_to_pay.sap_master.CsktId;


/**
 * ServiceImpl object for domain model class Cskt.
 *
 * @see Cskt
 */
@Service("sap_master.CsktService")
@Validated
@EntityService(entityClass = Cskt.class, serviceId = "sap_master")
public class CsktServiceImpl implements CsktService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsktServiceImpl.class);


    @Autowired
    @Qualifier("sap_master.CsktDao")
    private WMGenericDao<Cskt, CsktId> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<Cskt, CsktId> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "sap_masterTransactionManager")
    @Override
    public Cskt create(Cskt cskt) {
        LOGGER.debug("Creating a new Cskt with information: {}", cskt);

        Cskt csktCreated = this.wmGenericDao.create(cskt);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(csktCreated);
    }

    @Transactional(readOnly = true, value = "sap_masterTransactionManager")
    @Override
    public Cskt getById(CsktId csktId) {
        LOGGER.debug("Finding Cskt by id: {}", csktId);
        return this.wmGenericDao.findById(csktId);
    }

    @Transactional(readOnly = true, value = "sap_masterTransactionManager")
    @Override
    public Cskt findById(CsktId csktId) {
        LOGGER.debug("Finding Cskt by id: {}", csktId);
        try {
            return this.wmGenericDao.findById(csktId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No Cskt found with id: {}", csktId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "sap_masterTransactionManager")
    @Override
    public List<Cskt> findByMultipleIds(List<CsktId> csktIds, boolean orderedReturn) {
        LOGGER.debug("Finding Cskts by ids: {}", csktIds);

        return this.wmGenericDao.findByMultipleIds(csktIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "sap_masterTransactionManager")
    @Override
    public Cskt update(Cskt cskt) {
        LOGGER.debug("Updating Cskt with information: {}", cskt);

        this.wmGenericDao.update(cskt);
        this.wmGenericDao.refresh(cskt);

        return cskt;
    }

    @Transactional(value = "sap_masterTransactionManager")
    @Override
    public Cskt partialUpdate(CsktId csktId, Map<String, Object>csktPatch) {
        LOGGER.debug("Partially Updating the Cskt with id: {}", csktId);

        Cskt cskt = getById(csktId);

        try {
            ObjectReader csktReader = this.objectMapper.reader().forType(Cskt.class).withValueToUpdate(cskt);
            cskt = csktReader.readValue(this.objectMapper.writeValueAsString(csktPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", csktPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        cskt = update(cskt);

        return cskt;
    }

    @Transactional(value = "sap_masterTransactionManager")
    @Override
    public Cskt delete(CsktId csktId) {
        LOGGER.debug("Deleting Cskt with id: {}", csktId);
        Cskt deleted = this.wmGenericDao.findById(csktId);
        if (deleted == null) {
            LOGGER.debug("No Cskt found with id: {}", csktId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), Cskt.class.getSimpleName(), csktId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "sap_masterTransactionManager")
    @Override
    public void delete(Cskt cskt) {
        LOGGER.debug("Deleting Cskt with {}", cskt);
        this.wmGenericDao.delete(cskt);
    }

    @Transactional(readOnly = true, value = "sap_masterTransactionManager")
    @Override
    public Page<Cskt> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all Cskts");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "sap_masterTransactionManager")
    @Override
    public Page<Cskt> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all Cskts");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "sap_masterTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service sap_master for table Cskt to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "sap_masterTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service sap_master for table Cskt to {} format", options.getExportType());
        this.wmGenericDao.export(options, pageable, outputStream);
    }

    @Transactional(readOnly = true, value = "sap_masterTransactionManager")
    @Override
    public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "sap_masterTransactionManager")
    @Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }



}
