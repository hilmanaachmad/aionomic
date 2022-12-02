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

import id.co.aio.procure_to_pay.aio_ptp.TblMIo;
import id.co.aio.procure_to_pay.aio_ptp.TblTBudgetAdditional;


/**
 * ServiceImpl object for domain model class TblMIo.
 *
 * @see TblMIo
 */
@Service("aio_ptp.TblMIoService")
@Validated
@EntityService(entityClass = TblMIo.class, serviceId = "aio_ptp")
public class TblMIoServiceImpl implements TblMIoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblMIoServiceImpl.class);

    @Lazy
    @Autowired
    @Qualifier("aio_ptp.TblTBudgetAdditionalService")
    private TblTBudgetAdditionalService tblTBudgetAdditionalService;

    @Autowired
    @Qualifier("aio_ptp.TblMIoDao")
    private WMGenericDao<TblMIo, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TblMIo, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMIo create(TblMIo tblMio) {
        LOGGER.debug("Creating a new TblMIo with information: {}", tblMio);

        TblMIo tblMioCreated = this.wmGenericDao.create(tblMio);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tblMioCreated);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblMIo getById(Integer tblmioId) {
        LOGGER.debug("Finding TblMIo by id: {}", tblmioId);
        return this.wmGenericDao.findById(tblmioId);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblMIo findById(Integer tblmioId) {
        LOGGER.debug("Finding TblMIo by id: {}", tblmioId);
        try {
            return this.wmGenericDao.findById(tblmioId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TblMIo found with id: {}", tblmioId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public List<TblMIo> findByMultipleIds(List<Integer> tblmioIds, boolean orderedReturn) {
        LOGGER.debug("Finding TblMIos by ids: {}", tblmioIds);

        return this.wmGenericDao.findByMultipleIds(tblmioIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_ptpTransactionManager")
    @Override
    public TblMIo update(TblMIo tblMio) {
        LOGGER.debug("Updating TblMIo with information: {}", tblMio);

        this.wmGenericDao.update(tblMio);
        this.wmGenericDao.refresh(tblMio);

        return tblMio;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMIo partialUpdate(Integer tblmioId, Map<String, Object>tblMioPatch) {
        LOGGER.debug("Partially Updating the TblMIo with id: {}", tblmioId);

        TblMIo tblMio = getById(tblmioId);

        try {
            ObjectReader tblMioReader = this.objectMapper.reader().forType(TblMIo.class).withValueToUpdate(tblMio);
            tblMio = tblMioReader.readValue(this.objectMapper.writeValueAsString(tblMioPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tblMioPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tblMio = update(tblMio);

        return tblMio;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMIo delete(Integer tblmioId) {
        LOGGER.debug("Deleting TblMIo with id: {}", tblmioId);
        TblMIo deleted = this.wmGenericDao.findById(tblmioId);
        if (deleted == null) {
            LOGGER.debug("No TblMIo found with id: {}", tblmioId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TblMIo.class.getSimpleName(), tblmioId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public void delete(TblMIo tblMio) {
        LOGGER.debug("Deleting TblMIo with {}", tblMio);
        this.wmGenericDao.delete(tblMio);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblMIo> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TblMIos");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblMIo> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TblMIos");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblMIo to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblMIo to {} format", options.getExportType());
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
    public Page<TblTBudgetAdditional> findAssociatedTblTbudgetAdditionals(Integer ioId, Pageable pageable) {
        LOGGER.debug("Fetching all associated tblTbudgetAdditionals");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("tblMio.ioId = '" + ioId + "'");

        return tblTBudgetAdditionalService.findAll(queryBuilder.toString(), pageable);
    }

    /**
     * This setter method should only be used by unit tests
     *
     * @param service TblTBudgetAdditionalService instance
     */
    protected void setTblTBudgetAdditionalService(TblTBudgetAdditionalService service) {
        this.tblTBudgetAdditionalService = service;
    }

}