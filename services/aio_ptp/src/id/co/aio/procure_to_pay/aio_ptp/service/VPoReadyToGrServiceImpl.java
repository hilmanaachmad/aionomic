/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.service;

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

import id.co.aio.procure_to_pay.aio_ptp.VPoReadyToGr;


/**
 * ServiceImpl object for domain model class VPoReadyToGr.
 *
 * @see VPoReadyToGr
 */
@Service("aio_ptp.VPoReadyToGrService")
@Validated
@EntityService(entityClass = VPoReadyToGr.class, serviceId = "aio_ptp")
public class VPoReadyToGrServiceImpl implements VPoReadyToGrService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VPoReadyToGrServiceImpl.class);


    @Autowired
    @Qualifier("aio_ptp.VPoReadyToGrDao")
    private WMGenericDao<VPoReadyToGr, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<VPoReadyToGr, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public VPoReadyToGr create(VPoReadyToGr vpoReadyToGr) {
        LOGGER.debug("Creating a new VPoReadyToGr with information: {}", vpoReadyToGr);

        VPoReadyToGr vpoReadyToGrCreated = this.wmGenericDao.create(vpoReadyToGr);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(vpoReadyToGrCreated);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public VPoReadyToGr getById(Integer vporeadytogrId) {
        LOGGER.debug("Finding VPoReadyToGr by id: {}", vporeadytogrId);
        return this.wmGenericDao.findById(vporeadytogrId);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public VPoReadyToGr findById(Integer vporeadytogrId) {
        LOGGER.debug("Finding VPoReadyToGr by id: {}", vporeadytogrId);
        try {
            return this.wmGenericDao.findById(vporeadytogrId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No VPoReadyToGr found with id: {}", vporeadytogrId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public List<VPoReadyToGr> findByMultipleIds(List<Integer> vporeadytogrIds, boolean orderedReturn) {
        LOGGER.debug("Finding VPoReadyToGrs by ids: {}", vporeadytogrIds);

        return this.wmGenericDao.findByMultipleIds(vporeadytogrIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_ptpTransactionManager")
    @Override
    public VPoReadyToGr update(VPoReadyToGr vpoReadyToGr) {
        LOGGER.debug("Updating VPoReadyToGr with information: {}", vpoReadyToGr);

        this.wmGenericDao.update(vpoReadyToGr);
        this.wmGenericDao.refresh(vpoReadyToGr);

        return vpoReadyToGr;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public VPoReadyToGr partialUpdate(Integer vporeadytogrId, Map<String, Object>vpoReadyToGrPatch) {
        LOGGER.debug("Partially Updating the VPoReadyToGr with id: {}", vporeadytogrId);

        VPoReadyToGr vpoReadyToGr = getById(vporeadytogrId);

        try {
            ObjectReader vpoReadyToGrReader = this.objectMapper.reader().forType(VPoReadyToGr.class).withValueToUpdate(vpoReadyToGr);
            vpoReadyToGr = vpoReadyToGrReader.readValue(this.objectMapper.writeValueAsString(vpoReadyToGrPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", vpoReadyToGrPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        vpoReadyToGr = update(vpoReadyToGr);

        return vpoReadyToGr;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public VPoReadyToGr delete(Integer vporeadytogrId) {
        LOGGER.debug("Deleting VPoReadyToGr with id: {}", vporeadytogrId);
        VPoReadyToGr deleted = this.wmGenericDao.findById(vporeadytogrId);
        if (deleted == null) {
            LOGGER.debug("No VPoReadyToGr found with id: {}", vporeadytogrId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), VPoReadyToGr.class.getSimpleName(), vporeadytogrId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public void delete(VPoReadyToGr vpoReadyToGr) {
        LOGGER.debug("Deleting VPoReadyToGr with {}", vpoReadyToGr);
        this.wmGenericDao.delete(vpoReadyToGr);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<VPoReadyToGr> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all VPoReadyToGrs");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<VPoReadyToGr> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all VPoReadyToGrs");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_ptp for table VPoReadyToGr to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_ptp for table VPoReadyToGr to {} format", options.getExportType());
        this.wmGenericDao.export(options, pageable, outputStream);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }



}