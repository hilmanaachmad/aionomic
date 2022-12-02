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

import id.co.aio.procure_to_pay.aio_ptp.VBodDept;


/**
 * ServiceImpl object for domain model class VBodDept.
 *
 * @see VBodDept
 */
@Service("aio_ptp.VBodDeptService")
@Validated
@EntityService(entityClass = VBodDept.class, serviceId = "aio_ptp")
public class VBodDeptServiceImpl implements VBodDeptService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VBodDeptServiceImpl.class);


    @Autowired
    @Qualifier("aio_ptp.VBodDeptDao")
    private WMGenericDao<VBodDept, String> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<VBodDept, String> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public VBodDept create(VBodDept vbodDept) {
        LOGGER.debug("Creating a new VBodDept with information: {}", vbodDept);

        VBodDept vbodDeptCreated = this.wmGenericDao.create(vbodDept);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(vbodDeptCreated);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public VBodDept getById(String vboddeptId) {
        LOGGER.debug("Finding VBodDept by id: {}", vboddeptId);
        return this.wmGenericDao.findById(vboddeptId);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public VBodDept findById(String vboddeptId) {
        LOGGER.debug("Finding VBodDept by id: {}", vboddeptId);
        try {
            return this.wmGenericDao.findById(vboddeptId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No VBodDept found with id: {}", vboddeptId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public List<VBodDept> findByMultipleIds(List<String> vboddeptIds, boolean orderedReturn) {
        LOGGER.debug("Finding VBodDepts by ids: {}", vboddeptIds);

        return this.wmGenericDao.findByMultipleIds(vboddeptIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_ptpTransactionManager")
    @Override
    public VBodDept update(VBodDept vbodDept) {
        LOGGER.debug("Updating VBodDept with information: {}", vbodDept);

        this.wmGenericDao.update(vbodDept);
        this.wmGenericDao.refresh(vbodDept);

        return vbodDept;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public VBodDept partialUpdate(String vboddeptId, Map<String, Object>vbodDeptPatch) {
        LOGGER.debug("Partially Updating the VBodDept with id: {}", vboddeptId);

        VBodDept vbodDept = getById(vboddeptId);

        try {
            ObjectReader vbodDeptReader = this.objectMapper.reader().forType(VBodDept.class).withValueToUpdate(vbodDept);
            vbodDept = vbodDeptReader.readValue(this.objectMapper.writeValueAsString(vbodDeptPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", vbodDeptPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        vbodDept = update(vbodDept);

        return vbodDept;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public VBodDept delete(String vboddeptId) {
        LOGGER.debug("Deleting VBodDept with id: {}", vboddeptId);
        VBodDept deleted = this.wmGenericDao.findById(vboddeptId);
        if (deleted == null) {
            LOGGER.debug("No VBodDept found with id: {}", vboddeptId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), VBodDept.class.getSimpleName(), vboddeptId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public void delete(VBodDept vbodDept) {
        LOGGER.debug("Deleting VBodDept with {}", vbodDept);
        this.wmGenericDao.delete(vbodDept);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<VBodDept> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all VBodDepts");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<VBodDept> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all VBodDepts");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_ptp for table VBodDept to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_ptp for table VBodDept to {} format", options.getExportType());
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