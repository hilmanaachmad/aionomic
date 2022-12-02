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

import id.co.aio.procure_to_pay.aio_ptp.VGoodsRfq;


/**
 * ServiceImpl object for domain model class VGoodsRfq.
 *
 * @see VGoodsRfq
 */
@Service("aio_ptp.VGoodsRfqService")
@Validated
@EntityService(entityClass = VGoodsRfq.class, serviceId = "aio_ptp")
public class VGoodsRfqServiceImpl implements VGoodsRfqService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VGoodsRfqServiceImpl.class);


    @Autowired
    @Qualifier("aio_ptp.VGoodsRfqDao")
    private WMGenericDao<VGoodsRfq, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<VGoodsRfq, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public VGoodsRfq create(VGoodsRfq vgoodsRfq) {
        LOGGER.debug("Creating a new VGoodsRfq with information: {}", vgoodsRfq);

        VGoodsRfq vgoodsRfqCreated = this.wmGenericDao.create(vgoodsRfq);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(vgoodsRfqCreated);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public VGoodsRfq getById(Integer vgoodsrfqId) {
        LOGGER.debug("Finding VGoodsRfq by id: {}", vgoodsrfqId);
        return this.wmGenericDao.findById(vgoodsrfqId);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public VGoodsRfq findById(Integer vgoodsrfqId) {
        LOGGER.debug("Finding VGoodsRfq by id: {}", vgoodsrfqId);
        try {
            return this.wmGenericDao.findById(vgoodsrfqId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No VGoodsRfq found with id: {}", vgoodsrfqId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public List<VGoodsRfq> findByMultipleIds(List<Integer> vgoodsrfqIds, boolean orderedReturn) {
        LOGGER.debug("Finding VGoodsRfqs by ids: {}", vgoodsrfqIds);

        return this.wmGenericDao.findByMultipleIds(vgoodsrfqIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_ptpTransactionManager")
    @Override
    public VGoodsRfq update(VGoodsRfq vgoodsRfq) {
        LOGGER.debug("Updating VGoodsRfq with information: {}", vgoodsRfq);

        this.wmGenericDao.update(vgoodsRfq);
        this.wmGenericDao.refresh(vgoodsRfq);

        return vgoodsRfq;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public VGoodsRfq partialUpdate(Integer vgoodsrfqId, Map<String, Object>vgoodsRfqPatch) {
        LOGGER.debug("Partially Updating the VGoodsRfq with id: {}", vgoodsrfqId);

        VGoodsRfq vgoodsRfq = getById(vgoodsrfqId);

        try {
            ObjectReader vgoodsRfqReader = this.objectMapper.reader().forType(VGoodsRfq.class).withValueToUpdate(vgoodsRfq);
            vgoodsRfq = vgoodsRfqReader.readValue(this.objectMapper.writeValueAsString(vgoodsRfqPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", vgoodsRfqPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        vgoodsRfq = update(vgoodsRfq);

        return vgoodsRfq;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public VGoodsRfq delete(Integer vgoodsrfqId) {
        LOGGER.debug("Deleting VGoodsRfq with id: {}", vgoodsrfqId);
        VGoodsRfq deleted = this.wmGenericDao.findById(vgoodsrfqId);
        if (deleted == null) {
            LOGGER.debug("No VGoodsRfq found with id: {}", vgoodsrfqId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), VGoodsRfq.class.getSimpleName(), vgoodsrfqId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public void delete(VGoodsRfq vgoodsRfq) {
        LOGGER.debug("Deleting VGoodsRfq with {}", vgoodsRfq);
        this.wmGenericDao.delete(vgoodsRfq);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<VGoodsRfq> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all VGoodsRfqs");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<VGoodsRfq> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all VGoodsRfqs");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_ptp for table VGoodsRfq to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_ptp for table VGoodsRfq to {} format", options.getExportType());
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