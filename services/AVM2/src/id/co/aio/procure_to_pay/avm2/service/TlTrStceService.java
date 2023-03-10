/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2.service;

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

import id.co.aio.procure_to_pay.avm2.TlTrStce;
import id.co.aio.procure_to_pay.avm2.TlTrStceId;

/**
 * Service object for domain model class {@link TlTrStce}.
 */
public interface TlTrStceService {

    /**
     * Creates a new TlTrStce. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TlTrStce if any.
     *
     * @param tlTrStce Details of the TlTrStce to be created; value cannot be null.
     * @return The newly created TlTrStce.
     */
    TlTrStce create(@Valid TlTrStce tlTrStce);


	/**
     * Returns TlTrStce by given id if exists.
     *
     * @param tltrstceId The id of the TlTrStce to get; value cannot be null.
     * @return TlTrStce associated with the given tltrstceId.
	 * @throws EntityNotFoundException If no TlTrStce is found.
     */
    TlTrStce getById(TlTrStceId tltrstceId);

    /**
     * Find and return the TlTrStce by given id if exists, returns null otherwise.
     *
     * @param tltrstceId The id of the TlTrStce to get; value cannot be null.
     * @return TlTrStce associated with the given tltrstceId.
     */
    TlTrStce findById(TlTrStceId tltrstceId);

	/**
     * Find and return the list of TlTrStces by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param tltrstceIds The id's of the TlTrStce to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return TlTrStces associated with the given tltrstceIds.
     */
    List<TlTrStce> findByMultipleIds(List<TlTrStceId> tltrstceIds, boolean orderedReturn);


    /**
     * Updates the details of an existing TlTrStce. It replaces all fields of the existing TlTrStce with the given tlTrStce.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TlTrStce if any.
     *
     * @param tlTrStce The details of the TlTrStce to be updated; value cannot be null.
     * @return The updated TlTrStce.
     * @throws EntityNotFoundException if no TlTrStce is found with given input.
     */
    TlTrStce update(@Valid TlTrStce tlTrStce);


    /**
     * Partially updates the details of an existing TlTrStce. It updates only the
     * fields of the existing TlTrStce which are passed in the tlTrStcePatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TlTrStce if any.
     *
     * @param tltrstceId The id of the TlTrStce to be deleted; value cannot be null.
     * @param tlTrStcePatch The partial data of TlTrStce which is supposed to be updated; value cannot be null.
     * @return The updated TlTrStce.
     * @throws EntityNotFoundException if no TlTrStce is found with given input.
     */
    TlTrStce partialUpdate(TlTrStceId tltrstceId, Map<String, Object> tlTrStcePatch);

    /**
     * Deletes an existing TlTrStce with the given id.
     *
     * @param tltrstceId The id of the TlTrStce to be deleted; value cannot be null.
     * @return The deleted TlTrStce.
     * @throws EntityNotFoundException if no TlTrStce found with the given id.
     */
    TlTrStce delete(TlTrStceId tltrstceId);

    /**
     * Deletes an existing TlTrStce with the given object.
     *
     * @param tlTrStce The instance of the TlTrStce to be deleted; value cannot be null.
     */
    void delete(TlTrStce tlTrStce);

    /**
     * Find all TlTrStces matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TlTrStces.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<TlTrStce> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all TlTrStces matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TlTrStces.
     *
     * @see Pageable
     * @see Page
     */
    Page<TlTrStce> findAll(String query, Pageable pageable);

    /**
     * Exports all TlTrStces matching the given input query to the given exportType format.
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
     * Exports all TlTrStces matching the given input query to the given exportType format.
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
     * Retrieve the count of the TlTrStces in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the TlTrStce.
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


}