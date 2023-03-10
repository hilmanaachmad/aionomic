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

import id.co.aio.procure_to_pay.avm2.VPic;


/**
 * ServiceImpl object for domain model class VPic.
 *
 * @see VPic
 */
@Service("AVM2.VPicService")
@Validated
@EntityService(entityClass = VPic.class, serviceId = "AVM2")
public class VPicServiceImpl implements VPicService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VPicServiceImpl.class);


    @Autowired
    @Qualifier("AVM2.VPicDao")
    private WMGenericDao<VPic, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<VPic, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public VPic create(VPic vpic) {
        LOGGER.debug("Creating a new VPic with information: {}", vpic);

        VPic vpicCreated = this.wmGenericDao.create(vpic);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(vpicCreated);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public VPic getById(Integer vpicId) {
        LOGGER.debug("Finding VPic by id: {}", vpicId);
        return this.wmGenericDao.findById(vpicId);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public VPic findById(Integer vpicId) {
        LOGGER.debug("Finding VPic by id: {}", vpicId);
        try {
            return this.wmGenericDao.findById(vpicId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No VPic found with id: {}", vpicId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public List<VPic> findByMultipleIds(List<Integer> vpicIds, boolean orderedReturn) {
        LOGGER.debug("Finding VPics by ids: {}", vpicIds);

        return this.wmGenericDao.findByMultipleIds(vpicIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "AVM2TransactionManager")
    @Override
    public VPic update(VPic vpic) {
        LOGGER.debug("Updating VPic with information: {}", vpic);

        this.wmGenericDao.update(vpic);
        this.wmGenericDao.refresh(vpic);

        return vpic;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public VPic partialUpdate(Integer vpicId, Map<String, Object>vpicPatch) {
        LOGGER.debug("Partially Updating the VPic with id: {}", vpicId);

        VPic vpic = getById(vpicId);

        try {
            ObjectReader vpicReader = this.objectMapper.reader().forType(VPic.class).withValueToUpdate(vpic);
            vpic = vpicReader.readValue(this.objectMapper.writeValueAsString(vpicPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", vpicPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        vpic = update(vpic);

        return vpic;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public VPic delete(Integer vpicId) {
        LOGGER.debug("Deleting VPic with id: {}", vpicId);
        VPic deleted = this.wmGenericDao.findById(vpicId);
        if (deleted == null) {
            LOGGER.debug("No VPic found with id: {}", vpicId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), VPic.class.getSimpleName(), vpicId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public void delete(VPic vpic) {
        LOGGER.debug("Deleting VPic with {}", vpic);
        this.wmGenericDao.delete(vpic);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<VPic> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all VPics");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<VPic> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all VPics");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service AVM2 for table VPic to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service AVM2 for table VPic to {} format", options.getExportType());
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