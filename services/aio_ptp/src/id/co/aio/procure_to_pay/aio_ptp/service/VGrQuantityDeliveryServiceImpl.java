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

import id.co.aio.procure_to_pay.aio_ptp.VGrQuantityDelivery;


/**
 * ServiceImpl object for domain model class VGrQuantityDelivery.
 *
 * @see VGrQuantityDelivery
 */
@Service("aio_ptp.VGrQuantityDeliveryService")
@Validated
@EntityService(entityClass = VGrQuantityDelivery.class, serviceId = "aio_ptp")
public class VGrQuantityDeliveryServiceImpl implements VGrQuantityDeliveryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VGrQuantityDeliveryServiceImpl.class);


    @Autowired
    @Qualifier("aio_ptp.VGrQuantityDeliveryDao")
    private WMGenericDao<VGrQuantityDelivery, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<VGrQuantityDelivery, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public VGrQuantityDelivery create(VGrQuantityDelivery vgrQuantityDelivery) {
        LOGGER.debug("Creating a new VGrQuantityDelivery with information: {}", vgrQuantityDelivery);

        VGrQuantityDelivery vgrQuantityDeliveryCreated = this.wmGenericDao.create(vgrQuantityDelivery);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(vgrQuantityDeliveryCreated);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public VGrQuantityDelivery getById(Integer vgrquantitydeliveryId) {
        LOGGER.debug("Finding VGrQuantityDelivery by id: {}", vgrquantitydeliveryId);
        return this.wmGenericDao.findById(vgrquantitydeliveryId);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public VGrQuantityDelivery findById(Integer vgrquantitydeliveryId) {
        LOGGER.debug("Finding VGrQuantityDelivery by id: {}", vgrquantitydeliveryId);
        try {
            return this.wmGenericDao.findById(vgrquantitydeliveryId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No VGrQuantityDelivery found with id: {}", vgrquantitydeliveryId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public List<VGrQuantityDelivery> findByMultipleIds(List<Integer> vgrquantitydeliveryIds, boolean orderedReturn) {
        LOGGER.debug("Finding VGrQuantityDeliveries by ids: {}", vgrquantitydeliveryIds);

        return this.wmGenericDao.findByMultipleIds(vgrquantitydeliveryIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_ptpTransactionManager")
    @Override
    public VGrQuantityDelivery update(VGrQuantityDelivery vgrQuantityDelivery) {
        LOGGER.debug("Updating VGrQuantityDelivery with information: {}", vgrQuantityDelivery);

        this.wmGenericDao.update(vgrQuantityDelivery);
        this.wmGenericDao.refresh(vgrQuantityDelivery);

        return vgrQuantityDelivery;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public VGrQuantityDelivery partialUpdate(Integer vgrquantitydeliveryId, Map<String, Object>vgrQuantityDeliveryPatch) {
        LOGGER.debug("Partially Updating the VGrQuantityDelivery with id: {}", vgrquantitydeliveryId);

        VGrQuantityDelivery vgrQuantityDelivery = getById(vgrquantitydeliveryId);

        try {
            ObjectReader vgrQuantityDeliveryReader = this.objectMapper.reader().forType(VGrQuantityDelivery.class).withValueToUpdate(vgrQuantityDelivery);
            vgrQuantityDelivery = vgrQuantityDeliveryReader.readValue(this.objectMapper.writeValueAsString(vgrQuantityDeliveryPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", vgrQuantityDeliveryPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        vgrQuantityDelivery = update(vgrQuantityDelivery);

        return vgrQuantityDelivery;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public VGrQuantityDelivery delete(Integer vgrquantitydeliveryId) {
        LOGGER.debug("Deleting VGrQuantityDelivery with id: {}", vgrquantitydeliveryId);
        VGrQuantityDelivery deleted = this.wmGenericDao.findById(vgrquantitydeliveryId);
        if (deleted == null) {
            LOGGER.debug("No VGrQuantityDelivery found with id: {}", vgrquantitydeliveryId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), VGrQuantityDelivery.class.getSimpleName(), vgrquantitydeliveryId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public void delete(VGrQuantityDelivery vgrQuantityDelivery) {
        LOGGER.debug("Deleting VGrQuantityDelivery with {}", vgrQuantityDelivery);
        this.wmGenericDao.delete(vgrQuantityDelivery);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<VGrQuantityDelivery> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all VGrQuantityDeliveries");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<VGrQuantityDelivery> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all VGrQuantityDeliveries");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_ptp for table VGrQuantityDelivery to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_ptp for table VGrQuantityDelivery to {} format", options.getExportType());
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