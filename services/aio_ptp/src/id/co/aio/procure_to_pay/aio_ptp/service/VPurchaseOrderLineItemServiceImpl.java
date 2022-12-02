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

import id.co.aio.procure_to_pay.aio_ptp.VPurchaseOrderLineItem;
import id.co.aio.procure_to_pay.aio_ptp.VPurchaseOrderLineItemId;


/**
 * ServiceImpl object for domain model class VPurchaseOrderLineItem.
 *
 * @see VPurchaseOrderLineItem
 */
@Service("aio_ptp.VPurchaseOrderLineItemService")
@Validated
@EntityService(entityClass = VPurchaseOrderLineItem.class, serviceId = "aio_ptp")
public class VPurchaseOrderLineItemServiceImpl implements VPurchaseOrderLineItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VPurchaseOrderLineItemServiceImpl.class);


    @Autowired
    @Qualifier("aio_ptp.VPurchaseOrderLineItemDao")
    private WMGenericDao<VPurchaseOrderLineItem, VPurchaseOrderLineItemId> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<VPurchaseOrderLineItem, VPurchaseOrderLineItemId> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public VPurchaseOrderLineItem create(VPurchaseOrderLineItem vpurchaseOrderLineItem) {
        LOGGER.debug("Creating a new VPurchaseOrderLineItem with information: {}", vpurchaseOrderLineItem);

        VPurchaseOrderLineItem vpurchaseOrderLineItemCreated = this.wmGenericDao.create(vpurchaseOrderLineItem);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(vpurchaseOrderLineItemCreated);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public VPurchaseOrderLineItem getById(VPurchaseOrderLineItemId vpurchaseorderlineitemId) {
        LOGGER.debug("Finding VPurchaseOrderLineItem by id: {}", vpurchaseorderlineitemId);
        return this.wmGenericDao.findById(vpurchaseorderlineitemId);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public VPurchaseOrderLineItem findById(VPurchaseOrderLineItemId vpurchaseorderlineitemId) {
        LOGGER.debug("Finding VPurchaseOrderLineItem by id: {}", vpurchaseorderlineitemId);
        try {
            return this.wmGenericDao.findById(vpurchaseorderlineitemId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No VPurchaseOrderLineItem found with id: {}", vpurchaseorderlineitemId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public List<VPurchaseOrderLineItem> findByMultipleIds(List<VPurchaseOrderLineItemId> vpurchaseorderlineitemIds, boolean orderedReturn) {
        LOGGER.debug("Finding VPurchaseOrderLineItems by ids: {}", vpurchaseorderlineitemIds);

        return this.wmGenericDao.findByMultipleIds(vpurchaseorderlineitemIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_ptpTransactionManager")
    @Override
    public VPurchaseOrderLineItem update(VPurchaseOrderLineItem vpurchaseOrderLineItem) {
        LOGGER.debug("Updating VPurchaseOrderLineItem with information: {}", vpurchaseOrderLineItem);

        this.wmGenericDao.update(vpurchaseOrderLineItem);
        this.wmGenericDao.refresh(vpurchaseOrderLineItem);

        return vpurchaseOrderLineItem;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public VPurchaseOrderLineItem partialUpdate(VPurchaseOrderLineItemId vpurchaseorderlineitemId, Map<String, Object>vpurchaseOrderLineItemPatch) {
        LOGGER.debug("Partially Updating the VPurchaseOrderLineItem with id: {}", vpurchaseorderlineitemId);

        VPurchaseOrderLineItem vpurchaseOrderLineItem = getById(vpurchaseorderlineitemId);

        try {
            ObjectReader vpurchaseOrderLineItemReader = this.objectMapper.reader().forType(VPurchaseOrderLineItem.class).withValueToUpdate(vpurchaseOrderLineItem);
            vpurchaseOrderLineItem = vpurchaseOrderLineItemReader.readValue(this.objectMapper.writeValueAsString(vpurchaseOrderLineItemPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", vpurchaseOrderLineItemPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        vpurchaseOrderLineItem = update(vpurchaseOrderLineItem);

        return vpurchaseOrderLineItem;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public VPurchaseOrderLineItem delete(VPurchaseOrderLineItemId vpurchaseorderlineitemId) {
        LOGGER.debug("Deleting VPurchaseOrderLineItem with id: {}", vpurchaseorderlineitemId);
        VPurchaseOrderLineItem deleted = this.wmGenericDao.findById(vpurchaseorderlineitemId);
        if (deleted == null) {
            LOGGER.debug("No VPurchaseOrderLineItem found with id: {}", vpurchaseorderlineitemId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), VPurchaseOrderLineItem.class.getSimpleName(), vpurchaseorderlineitemId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public void delete(VPurchaseOrderLineItem vpurchaseOrderLineItem) {
        LOGGER.debug("Deleting VPurchaseOrderLineItem with {}", vpurchaseOrderLineItem);
        this.wmGenericDao.delete(vpurchaseOrderLineItem);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<VPurchaseOrderLineItem> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all VPurchaseOrderLineItems");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<VPurchaseOrderLineItem> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all VPurchaseOrderLineItems");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_ptp for table VPurchaseOrderLineItem to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_ptp for table VPurchaseOrderLineItem to {} format", options.getExportType());
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