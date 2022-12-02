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

import id.co.aio.procure_to_pay.avm2.TlMsCc;

/**
 * Service object for domain model class {@link TlMsCc}.
 */
public interface TlMsCcService {

    /**
     * Creates a new TlMsCc. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TlMsCc if any.
     *
     * @param tlMsCc Details of the TlMsCc to be created; value cannot be null.
     * @return The newly created TlMsCc.
     */
    TlMsCc create(@Valid TlMsCc tlMsCc);


	/**
     * Returns TlMsCc by given id if exists.
     *
     * @param tlmsccId The id of the TlMsCc to get; value cannot be null.
     * @return TlMsCc associated with the given tlmsccId.
	 * @throws EntityNotFoundException If no TlMsCc is found.
     */
    TlMsCc getById(String tlmsccId);

    /**
     * Find and return the TlMsCc by given id if exists, returns null otherwise.
     *
     * @param tlmsccId The id of the TlMsCc to get; value cannot be null.
     * @return TlMsCc associated with the given tlmsccId.
     */
    TlMsCc findById(String tlmsccId);

	/**
     * Find and return the list of TlMsCcs by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param tlmsccIds The id's of the TlMsCc to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return TlMsCcs associated with the given tlmsccIds.
     */
    List<TlMsCc> findByMultipleIds(List<String> tlmsccIds, boolean orderedReturn);


    /**
     * Updates the details of an existing TlMsCc. It replaces all fields of the existing TlMsCc with the given tlMsCc.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TlMsCc if any.
     *
     * @param tlMsCc The details of the TlMsCc to be updated; value cannot be null.
     * @return The updated TlMsCc.
     * @throws EntityNotFoundException if no TlMsCc is found with given input.
     */
    TlMsCc update(@Valid TlMsCc tlMsCc);


    /**
     * Partially updates the details of an existing TlMsCc. It updates only the
     * fields of the existing TlMsCc which are passed in the tlMsCcPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TlMsCc if any.
     *
     * @param tlmsccId The id of the TlMsCc to be deleted; value cannot be null.
     * @param tlMsCcPatch The partial data of TlMsCc which is supposed to be updated; value cannot be null.
     * @return The updated TlMsCc.
     * @throws EntityNotFoundException if no TlMsCc is found with given input.
     */
    TlMsCc partialUpdate(String tlmsccId, Map<String, Object> tlMsCcPatch);

    /**
     * Deletes an existing TlMsCc with the given id.
     *
     * @param tlmsccId The id of the TlMsCc to be deleted; value cannot be null.
     * @return The deleted TlMsCc.
     * @throws EntityNotFoundException if no TlMsCc found with the given id.
     */
    TlMsCc delete(String tlmsccId);

    /**
     * Deletes an existing TlMsCc with the given object.
     *
     * @param tlMsCc The instance of the TlMsCc to be deleted; value cannot be null.
     */
    void delete(TlMsCc tlMsCc);

    /**
     * Find all TlMsCcs matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TlMsCcs.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<TlMsCc> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all TlMsCcs matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TlMsCcs.
     *
     * @see Pageable
     * @see Page
     */
    Page<TlMsCc> findAll(String query, Pageable pageable);

    /**
     * Exports all TlMsCcs matching the given input query to the given exportType format.
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
     * Exports all TlMsCcs matching the given input query to the given exportType format.
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
     * Retrieve the count of the TlMsCcs in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the TlMsCc.
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