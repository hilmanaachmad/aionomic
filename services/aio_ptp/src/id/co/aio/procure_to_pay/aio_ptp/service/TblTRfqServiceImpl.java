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
import org.springframework.context.annotation.Lazy;
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

import id.co.aio.procure_to_pay.aio_ptp.TblTRfq;
import id.co.aio.procure_to_pay.aio_ptp.TblTRfqVendor;


/**
 * ServiceImpl object for domain model class TblTRfq.
 *
 * @see TblTRfq
 */
@Service("aio_ptp.TblTRfqService")
@Validated
@EntityService(entityClass = TblTRfq.class, serviceId = "aio_ptp")
public class TblTRfqServiceImpl implements TblTRfqService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblTRfqServiceImpl.class);

    @Lazy
    @Autowired
    @Qualifier("aio_ptp.TblTRfqVendorService")
    private TblTRfqVendorService tblTRfqVendorService;

    @Autowired
    @Qualifier("aio_ptp.TblTRfqDao")
    private WMGenericDao<TblTRfq, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TblTRfq, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblTRfq create(TblTRfq tblTrfq) {
        LOGGER.debug("Creating a new TblTRfq with information: {}", tblTrfq);

        TblTRfq tblTrfqCreated = this.wmGenericDao.create(tblTrfq);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tblTrfqCreated);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblTRfq getById(Integer tbltrfqId) {
        LOGGER.debug("Finding TblTRfq by id: {}", tbltrfqId);
        return this.wmGenericDao.findById(tbltrfqId);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblTRfq findById(Integer tbltrfqId) {
        LOGGER.debug("Finding TblTRfq by id: {}", tbltrfqId);
        try {
            return this.wmGenericDao.findById(tbltrfqId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TblTRfq found with id: {}", tbltrfqId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public List<TblTRfq> findByMultipleIds(List<Integer> tbltrfqIds, boolean orderedReturn) {
        LOGGER.debug("Finding TblTRfqs by ids: {}", tbltrfqIds);

        return this.wmGenericDao.findByMultipleIds(tbltrfqIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_ptpTransactionManager")
    @Override
    public TblTRfq update(TblTRfq tblTrfq) {
        LOGGER.debug("Updating TblTRfq with information: {}", tblTrfq);

        this.wmGenericDao.update(tblTrfq);
        this.wmGenericDao.refresh(tblTrfq);

        return tblTrfq;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblTRfq partialUpdate(Integer tbltrfqId, Map<String, Object>tblTrfqPatch) {
        LOGGER.debug("Partially Updating the TblTRfq with id: {}", tbltrfqId);

        TblTRfq tblTrfq = getById(tbltrfqId);

        try {
            ObjectReader tblTrfqReader = this.objectMapper.reader().forType(TblTRfq.class).withValueToUpdate(tblTrfq);
            tblTrfq = tblTrfqReader.readValue(this.objectMapper.writeValueAsString(tblTrfqPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tblTrfqPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tblTrfq = update(tblTrfq);

        return tblTrfq;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblTRfq delete(Integer tbltrfqId) {
        LOGGER.debug("Deleting TblTRfq with id: {}", tbltrfqId);
        TblTRfq deleted = this.wmGenericDao.findById(tbltrfqId);
        if (deleted == null) {
            LOGGER.debug("No TblTRfq found with id: {}", tbltrfqId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TblTRfq.class.getSimpleName(), tbltrfqId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public void delete(TblTRfq tblTrfq) {
        LOGGER.debug("Deleting TblTRfq with {}", tblTrfq);
        this.wmGenericDao.delete(tblTrfq);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblTRfq> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TblTRfqs");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblTRfq> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TblTRfqs");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblTRfq to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblTRfq to {} format", options.getExportType());
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

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblTRfqVendor> findAssociatedTblTrfqVendors(Integer rfqId, Pageable pageable) {
        LOGGER.debug("Fetching all associated tblTrfqVendors");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("tblTrfq.rfqId = '" + rfqId + "'");

        return tblTRfqVendorService.findAll(queryBuilder.toString(), pageable);
    }

    /**
     * This setter method should only be used by unit tests
     *
     * @param service TblTRfqVendorService instance
     */
    protected void setTblTRfqVendorService(TblTRfqVendorService service) {
        this.tblTRfqVendorService = service;
    }

}