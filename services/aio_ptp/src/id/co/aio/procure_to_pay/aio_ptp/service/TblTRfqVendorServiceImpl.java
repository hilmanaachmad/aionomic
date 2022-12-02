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

import id.co.aio.procure_to_pay.aio_ptp.TblTRfqVenQuotation;
import id.co.aio.procure_to_pay.aio_ptp.TblTRfqVendor;


/**
 * ServiceImpl object for domain model class TblTRfqVendor.
 *
 * @see TblTRfqVendor
 */
@Service("aio_ptp.TblTRfqVendorService")
@Validated
@EntityService(entityClass = TblTRfqVendor.class, serviceId = "aio_ptp")
public class TblTRfqVendorServiceImpl implements TblTRfqVendorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblTRfqVendorServiceImpl.class);

    @Lazy
    @Autowired
    @Qualifier("aio_ptp.TblTRfqVenQuotationService")
    private TblTRfqVenQuotationService tblTRfqVenQuotationService;

    @Autowired
    @Qualifier("aio_ptp.TblTRfqVendorDao")
    private WMGenericDao<TblTRfqVendor, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TblTRfqVendor, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblTRfqVendor create(TblTRfqVendor tblTrfqVendor) {
        LOGGER.debug("Creating a new TblTRfqVendor with information: {}", tblTrfqVendor);

        TblTRfqVendor tblTrfqVendorCreated = this.wmGenericDao.create(tblTrfqVendor);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tblTrfqVendorCreated);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblTRfqVendor getById(Integer tbltrfqvendorId) {
        LOGGER.debug("Finding TblTRfqVendor by id: {}", tbltrfqvendorId);
        return this.wmGenericDao.findById(tbltrfqvendorId);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblTRfqVendor findById(Integer tbltrfqvendorId) {
        LOGGER.debug("Finding TblTRfqVendor by id: {}", tbltrfqvendorId);
        try {
            return this.wmGenericDao.findById(tbltrfqvendorId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TblTRfqVendor found with id: {}", tbltrfqvendorId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public List<TblTRfqVendor> findByMultipleIds(List<Integer> tbltrfqvendorIds, boolean orderedReturn) {
        LOGGER.debug("Finding TblTRfqVendors by ids: {}", tbltrfqvendorIds);

        return this.wmGenericDao.findByMultipleIds(tbltrfqvendorIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_ptpTransactionManager")
    @Override
    public TblTRfqVendor update(TblTRfqVendor tblTrfqVendor) {
        LOGGER.debug("Updating TblTRfqVendor with information: {}", tblTrfqVendor);

        this.wmGenericDao.update(tblTrfqVendor);
        this.wmGenericDao.refresh(tblTrfqVendor);

        return tblTrfqVendor;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblTRfqVendor partialUpdate(Integer tbltrfqvendorId, Map<String, Object>tblTrfqVendorPatch) {
        LOGGER.debug("Partially Updating the TblTRfqVendor with id: {}", tbltrfqvendorId);

        TblTRfqVendor tblTrfqVendor = getById(tbltrfqvendorId);

        try {
            ObjectReader tblTrfqVendorReader = this.objectMapper.reader().forType(TblTRfqVendor.class).withValueToUpdate(tblTrfqVendor);
            tblTrfqVendor = tblTrfqVendorReader.readValue(this.objectMapper.writeValueAsString(tblTrfqVendorPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tblTrfqVendorPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tblTrfqVendor = update(tblTrfqVendor);

        return tblTrfqVendor;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblTRfqVendor delete(Integer tbltrfqvendorId) {
        LOGGER.debug("Deleting TblTRfqVendor with id: {}", tbltrfqvendorId);
        TblTRfqVendor deleted = this.wmGenericDao.findById(tbltrfqvendorId);
        if (deleted == null) {
            LOGGER.debug("No TblTRfqVendor found with id: {}", tbltrfqvendorId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TblTRfqVendor.class.getSimpleName(), tbltrfqvendorId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public void delete(TblTRfqVendor tblTrfqVendor) {
        LOGGER.debug("Deleting TblTRfqVendor with {}", tblTrfqVendor);
        this.wmGenericDao.delete(tblTrfqVendor);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblTRfqVendor> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TblTRfqVendors");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblTRfqVendor> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TblTRfqVendors");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblTRfqVendor to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblTRfqVendor to {} format", options.getExportType());
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
    public Page<TblTRfqVenQuotation> findAssociatedTblTrfqVenQuotations(Integer rfqvId, Pageable pageable) {
        LOGGER.debug("Fetching all associated tblTrfqVenQuotations");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("tblTrfqVendor.rfqvId = '" + rfqvId + "'");

        return tblTRfqVenQuotationService.findAll(queryBuilder.toString(), pageable);
    }

    /**
     * This setter method should only be used by unit tests
     *
     * @param service TblTRfqVenQuotationService instance
     */
    protected void setTblTRfqVenQuotationService(TblTRfqVenQuotationService service) {
        this.tblTRfqVenQuotationService = service;
    }

}