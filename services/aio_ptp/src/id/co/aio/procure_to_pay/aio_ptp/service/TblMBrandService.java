/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import id.co.aio.procure_to_pay.aio_ptp.TblMBrand;
import id.co.aio.procure_to_pay.aio_ptp.TblMIo;
import id.co.aio.procure_to_pay.aio_ptp.TblTBudgetHeader;

/**
 * Service object for domain model class {@link TblMBrand}.
 */
public interface TblMBrandService {

    /**
     * Creates a new TblMBrand. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblMBrand if any.
     *
     * @param tblMbrand Details of the TblMBrand to be created; value cannot be null.
     * @return The newly created TblMBrand.
     */
    TblMBrand create(@Valid TblMBrand tblMbrand);


	/**
     * Returns TblMBrand by given id if exists.
     *
     * @param tblmbrandId The id of the TblMBrand to get; value cannot be null.
     * @return TblMBrand associated with the given tblmbrandId.
	 * @throws EntityNotFoundException If no TblMBrand is found.
     */
    TblMBrand getById(Integer tblmbrandId);

    /**
     * Find and return the TblMBrand by given id if exists, returns null otherwise.
     *
     * @param tblmbrandId The id of the TblMBrand to get; value cannot be null.
     * @return TblMBrand associated with the given tblmbrandId.
     */
    TblMBrand findById(Integer tblmbrandId);

	/**
     * Find and return the list of TblMBrands by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param tblmbrandIds The id's of the TblMBrand to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return TblMBrands associated with the given tblmbrandIds.
     */
    List<TblMBrand> findByMultipleIds(List<Integer> tblmbrandIds, boolean orderedReturn);


    /**
     * Updates the details of an existing TblMBrand. It replaces all fields of the existing TblMBrand with the given tblMbrand.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblMBrand if any.
     *
     * @param tblMbrand The details of the TblMBrand to be updated; value cannot be null.
     * @return The updated TblMBrand.
     * @throws EntityNotFoundException if no TblMBrand is found with given input.
     */
    TblMBrand update(@Valid TblMBrand tblMbrand);


    /**
     * Partially updates the details of an existing TblMBrand. It updates only the
     * fields of the existing TblMBrand which are passed in the tblMbrandPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblMBrand if any.
     *
     * @param tblmbrandId The id of the TblMBrand to be deleted; value cannot be null.
     * @param tblMbrandPatch The partial data of TblMBrand which is supposed to be updated; value cannot be null.
     * @return The updated TblMBrand.
     * @throws EntityNotFoundException if no TblMBrand is found with given input.
     */
    TblMBrand partialUpdate(Integer tblmbrandId, Map<String, Object> tblMbrandPatch);

    /**
     * Deletes an existing TblMBrand with the given id.
     *
     * @param tblmbrandId The id of the TblMBrand to be deleted; value cannot be null.
     * @return The deleted TblMBrand.
     * @throws EntityNotFoundException if no TblMBrand found with the given id.
     */
    TblMBrand delete(Integer tblmbrandId);

    /**
     * Deletes an existing TblMBrand with the given object.
     *
     * @param tblMbrand The instance of the TblMBrand to be deleted; value cannot be null.
     */
    void delete(TblMBrand tblMbrand);

    /**
     * Find all TblMBrands matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TblMBrands.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<TblMBrand> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all TblMBrands matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TblMBrands.
     *
     * @see Pageable
     * @see Page
     */
    Page<TblMBrand> findAll(String query, Pageable pageable);

    /**
     * Exports all TblMBrands matching the given input query to the given exportType format.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param exportType The format in which to export the data; value cannot be null.
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return The Downloadable file in given export type.
     *
     * @see Pageable
     * @see ExportType
     * @see Downloadable
     */
    Downloadable export(ExportType exportType, String query, Pageable pageable);

    /**
     * Exports all TblMBrands matching the given input query to the given exportType format.
     *
     * @param options The export options provided by the user; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @param outputStream The output stream of the file for the exported data to be written to.
     *
     * @see DataExportOptions
     * @see Pageable
     * @see OutputStream
     */
    void export(DataExportOptions options, Pageable pageable, OutputStream outputStream);

    /**
     * Retrieve the count of the TblMBrands in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the TblMBrand.
     */
    long count(String query);

    /**
     * Retrieve aggregated values with matching aggregation info.
     *
     * @param aggregationInfo info related to aggregations.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return Paginated data with included fields.
     *
     * @see AggregationInfo
     * @see Pageable
     * @see Page
	 */
    Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable);

    /*
     * Returns the associated tblMios for given TblMBrand id.
     *
     * @param brId value of brId; value cannot be null
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of associated TblMIo instances.
     *
     * @see Pageable
     * @see Page
     */
    Page<TblMIo> findAssociatedTblMios(Integer brId, Pageable pageable);

    /*
     * Returns the associated tblTbudgetHeaders for given TblMBrand id.
     *
     * @param brId value of brId; value cannot be null
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of associated TblTBudgetHeader instances.
     *
     * @see Pageable
     * @see Page
     */
    Page<TblTBudgetHeader> findAssociatedTblTbudgetHeaders(Integer brId, Pageable pageable);

}