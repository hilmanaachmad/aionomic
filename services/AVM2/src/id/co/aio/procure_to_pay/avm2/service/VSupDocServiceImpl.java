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

import id.co.aio.procure_to_pay.avm2.VSupDoc;


/**
 * ServiceImpl object for domain model class VSupDoc.
 *
 * @see VSupDoc
 */
@Service("AVM2.VSupDocService")
@Validated
@EntityService(entityClass = VSupDoc.class, serviceId = "AVM2")
public class VSupDocServiceImpl implements VSupDocService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VSupDocServiceImpl.class);


    @Autowired
    @Qualifier("AVM2.VSupDocDao")
    private WMGenericDao<VSupDoc, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<VSupDoc, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public VSupDoc create(VSupDoc vsupDoc) {
        LOGGER.debug("Creating a new VSupDoc with information: {}", vsupDoc);

        VSupDoc vsupDocCreated = this.wmGenericDao.create(vsupDoc);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(vsupDocCreated);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public VSupDoc getById(Integer vsupdocId) {
        LOGGER.debug("Finding VSupDoc by id: {}", vsupdocId);
        return this.wmGenericDao.findById(vsupdocId);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public VSupDoc findById(Integer vsupdocId) {
        LOGGER.debug("Finding VSupDoc by id: {}", vsupdocId);
        try {
            return this.wmGenericDao.findById(vsupdocId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No VSupDoc found with id: {}", vsupdocId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public List<VSupDoc> findByMultipleIds(List<Integer> vsupdocIds, boolean orderedReturn) {
        LOGGER.debug("Finding VSupDocs by ids: {}", vsupdocIds);

        return this.wmGenericDao.findByMultipleIds(vsupdocIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "AVM2TransactionManager")
    @Override
    public VSupDoc update(VSupDoc vsupDoc) {
        LOGGER.debug("Updating VSupDoc with information: {}", vsupDoc);

        this.wmGenericDao.update(vsupDoc);
        this.wmGenericDao.refresh(vsupDoc);

        return vsupDoc;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public VSupDoc partialUpdate(Integer vsupdocId, Map<String, Object>vsupDocPatch) {
        LOGGER.debug("Partially Updating the VSupDoc with id: {}", vsupdocId);

        VSupDoc vsupDoc = getById(vsupdocId);

        try {
            ObjectReader vsupDocReader = this.objectMapper.reader().forType(VSupDoc.class).withValueToUpdate(vsupDoc);
            vsupDoc = vsupDocReader.readValue(this.objectMapper.writeValueAsString(vsupDocPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", vsupDocPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        vsupDoc = update(vsupDoc);

        return vsupDoc;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public VSupDoc delete(Integer vsupdocId) {
        LOGGER.debug("Deleting VSupDoc with id: {}", vsupdocId);
        VSupDoc deleted = this.wmGenericDao.findById(vsupdocId);
        if (deleted == null) {
            LOGGER.debug("No VSupDoc found with id: {}", vsupdocId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), VSupDoc.class.getSimpleName(), vsupdocId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public void delete(VSupDoc vsupDoc) {
        LOGGER.debug("Deleting VSupDoc with {}", vsupDoc);
        this.wmGenericDao.delete(vsupDoc);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<VSupDoc> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all VSupDocs");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<VSupDoc> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all VSupDocs");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service AVM2 for table VSupDoc to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service AVM2 for table VSupDoc to {} format", options.getExportType());
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