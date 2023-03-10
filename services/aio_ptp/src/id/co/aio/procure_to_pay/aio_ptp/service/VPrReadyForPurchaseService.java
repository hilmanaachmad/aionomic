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

import id.co.aio.procure_to_pay.aio_ptp.VPrReadyForPurchase;

/**
 * Service object for domain model class {@link VPrReadyForPurchase}.
 */
public interface VPrReadyForPurchaseService {

    /**
     * Creates a new VPrReadyForPurchase. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VPrReadyForPurchase if any.
     *
     * @param vprReadyForPurchase Details of the VPrReadyForPurchase to be created; value cannot be null.
     * @return The newly created VPrReadyForPurchase.
     */
    VPrReadyForPurchase create(@Valid VPrReadyForPurchase vprReadyForPurchase);


	/**
     * Returns VPrReadyForPurchase by given id if exists.
     *
     * @param vprreadyforpurchaseId The id of the VPrReadyForPurchase to get; value cannot be null.
     * @return VPrReadyForPurchase associated with the given vprreadyforpurchaseId.
	 * @throws EntityNotFoundException If no VPrReadyForPurchase is found.
     */
    VPrReadyForPurchase getById(Integer vprreadyforpurchaseId);

    /**
     * Find and return the VPrReadyForPurchase by given id if exists, returns null otherwise.
     *
     * @param vprreadyforpurchaseId The id of the VPrReadyForPurchase to get; value cannot be null.
     * @return VPrReadyForPurchase associated with the given vprreadyforpurchaseId.
     */
    VPrReadyForPurchase findById(Integer vprreadyforpurchaseId);

	/**
     * Find and return the list of VPrReadyForPurchases by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param vprreadyforpurchaseIds The id's of the VPrReadyForPurchase to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return VPrReadyForPurchases associated with the given vprreadyforpurchaseIds.
     */
    List<VPrReadyForPurchase> findByMultipleIds(List<Integer> vprreadyforpurchaseIds, boolean orderedReturn);


    /**
     * Updates the details of an existing VPrReadyForPurchase. It replaces all fields of the existing VPrReadyForPurchase with the given vprReadyForPurchase.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VPrReadyForPurchase if any.
     *
     * @param vprReadyForPurchase The details of the VPrReadyForPurchase to be updated; value cannot be null.
     * @return The updated VPrReadyForPurchase.
     * @throws EntityNotFoundException if no VPrReadyForPurchase is found with given input.
     */
    VPrReadyForPurchase update(@Valid VPrReadyForPurchase vprReadyForPurchase);


    /**
     * Partially updates the details of an existing VPrReadyForPurchase. It updates only the
     * fields of the existing VPrReadyForPurchase which are passed in the vprReadyForPurchasePatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VPrReadyForPurchase if any.
     *
     * @param vprreadyforpurchaseId The id of the VPrReadyForPurchase to be deleted; value cannot be null.
     * @param vprReadyForPurchasePatch The partial data of VPrReadyForPurchase which is supposed to be updated; value cannot be null.
     * @return The updated VPrReadyForPurchase.
     * @throws EntityNotFoundException if no VPrReadyForPurchase is found with given input.
     */
    VPrReadyForPurchase partialUpdate(Integer vprreadyforpurchaseId, Map<String, Object> vprReadyForPurchasePatch);

    /**
     * Deletes an existing VPrReadyForPurchase with the given id.
     *
     * @param vprreadyforpurchaseId The id of the VPrReadyForPurchase to be deleted; value cannot be null.
     * @return The deleted VPrReadyForPurchase.
     * @throws EntityNotFoundException if no VPrReadyForPurchase found with the given id.
     */
    VPrReadyForPurchase delete(Integer vprreadyforpurchaseId);

    /**
     * Deletes an existing VPrReadyForPurchase with the given object.
     *
     * @param vprReadyForPurchase The instance of the VPrReadyForPurchase to be deleted; value cannot be null.
     */
    void delete(VPrReadyForPurchase vprReadyForPurchase);

    /**
     * Find all VPrReadyForPurchases matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching VPrReadyForPurchases.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<VPrReadyForPurchase> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all VPrReadyForPurchases matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching VPrReadyForPurchases.
     *
     * @see Pageable
     * @see Page
     */
    Page<VPrReadyForPurchase> findAll(String query, Pageable pageable);

    /**
     * Exports all VPrReadyForPurchases matching the given input query to the given exportType format.
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
     * Exports all VPrReadyForPurchases matching the given input query to the given exportType format.
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
     * Retrieve the count of the VPrReadyForPurchases in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the VPrReadyForPurchase.
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