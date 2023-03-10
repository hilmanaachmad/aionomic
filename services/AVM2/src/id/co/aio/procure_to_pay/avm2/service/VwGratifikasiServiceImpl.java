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

import id.co.aio.procure_to_pay.avm2.VwGratifikasi;


/**
 * ServiceImpl object for domain model class VwGratifikasi.
 *
 * @see VwGratifikasi
 */
@Service("AVM2.VwGratifikasiService")
@Validated
@EntityService(entityClass = VwGratifikasi.class, serviceId = "AVM2")
public class VwGratifikasiServiceImpl implements VwGratifikasiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VwGratifikasiServiceImpl.class);


    @Autowired
    @Qualifier("AVM2.VwGratifikasiDao")
    private WMGenericDao<VwGratifikasi, String> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<VwGratifikasi, String> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public VwGratifikasi create(VwGratifikasi vwGratifikasi) {
        LOGGER.debug("Creating a new VwGratifikasi with information: {}", vwGratifikasi);

        VwGratifikasi vwGratifikasiCreated = this.wmGenericDao.create(vwGratifikasi);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(vwGratifikasiCreated);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public VwGratifikasi getById(String vwgratifikasiId) {
        LOGGER.debug("Finding VwGratifikasi by id: {}", vwgratifikasiId);
        return this.wmGenericDao.findById(vwgratifikasiId);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public VwGratifikasi findById(String vwgratifikasiId) {
        LOGGER.debug("Finding VwGratifikasi by id: {}", vwgratifikasiId);
        try {
            return this.wmGenericDao.findById(vwgratifikasiId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No VwGratifikasi found with id: {}", vwgratifikasiId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public List<VwGratifikasi> findByMultipleIds(List<String> vwgratifikasiIds, boolean orderedReturn) {
        LOGGER.debug("Finding VwGratifikasis by ids: {}", vwgratifikasiIds);

        return this.wmGenericDao.findByMultipleIds(vwgratifikasiIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "AVM2TransactionManager")
    @Override
    public VwGratifikasi update(VwGratifikasi vwGratifikasi) {
        LOGGER.debug("Updating VwGratifikasi with information: {}", vwGratifikasi);

        this.wmGenericDao.update(vwGratifikasi);
        this.wmGenericDao.refresh(vwGratifikasi);

        return vwGratifikasi;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public VwGratifikasi partialUpdate(String vwgratifikasiId, Map<String, Object>vwGratifikasiPatch) {
        LOGGER.debug("Partially Updating the VwGratifikasi with id: {}", vwgratifikasiId);

        VwGratifikasi vwGratifikasi = getById(vwgratifikasiId);

        try {
            ObjectReader vwGratifikasiReader = this.objectMapper.reader().forType(VwGratifikasi.class).withValueToUpdate(vwGratifikasi);
            vwGratifikasi = vwGratifikasiReader.readValue(this.objectMapper.writeValueAsString(vwGratifikasiPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", vwGratifikasiPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        vwGratifikasi = update(vwGratifikasi);

        return vwGratifikasi;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public VwGratifikasi delete(String vwgratifikasiId) {
        LOGGER.debug("Deleting VwGratifikasi with id: {}", vwgratifikasiId);
        VwGratifikasi deleted = this.wmGenericDao.findById(vwgratifikasiId);
        if (deleted == null) {
            LOGGER.debug("No VwGratifikasi found with id: {}", vwgratifikasiId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), VwGratifikasi.class.getSimpleName(), vwgratifikasiId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public void delete(VwGratifikasi vwGratifikasi) {
        LOGGER.debug("Deleting VwGratifikasi with {}", vwGratifikasi);
        this.wmGenericDao.delete(vwGratifikasi);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<VwGratifikasi> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all VwGratifikasis");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<VwGratifikasi> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all VwGratifikasis");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service AVM2 for table VwGratifikasi to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service AVM2 for table VwGratifikasi to {} format", options.getExportType());
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